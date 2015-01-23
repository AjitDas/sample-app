package com.capgemini.user.logging.aop;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

import com.capgemini.user.exception.UserException;
import com.capgemini.user.exception.core.ExceptionHandler;
import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.ExceptionReturnLogEvent;
import com.capgemini.user.service.util.FirstFailExceptionThreadLocalHolder;
import com.capgemini.user.service.util.UserExceptionThreadLocalHolder;

@Aspect @Order(1) //@Component("exceptionHandlerAspect")
public class ExceptionHandlerAspect implements ApplicationContextAware {

	private Properties errorMappingProps= new Properties();
	
	private ApplicationContext applicationConext;
	
	@Value("${app.name:user-app}")
	private String appname;

	@Value("${error.mapping.properties.file:default-error-mapping.properties}")
	private String errorMappingPropFile;

	@Autowired @Qualifier("logPublisher") 
	private LogPublisher logPublisher;
	
	@Autowired @Qualifier("defaultExceptionHandler")
	private ExceptionHandler defaultExceptionHandler;
	
	//@Autowired @Lazy @Qualifier("customExceptionHandler")
	private ExceptionHandler customExceptionHandler;
	
	@Pointcut("execution(public * com.capgemini..*(..) throws !com.capgemini.user.exception.UserException+) && within(com.capgemini..*) && !within(com.capgemini.user.logging.aop..*) && !within(com.sun.proxy..*Proxy*)"
			+ "&& !within(com.capgemini..*Test) && !within(com.capgemini..Test*)")
	public void exceptionPointcut(){}

	@PostConstruct
	public void initialize(){
		if (this.errorMappingPropFile != null) {
			InputStream stream=null;
			try {
				stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(errorMappingPropFile);
				if (stream != null) {
					errorMappingProps.load(stream);
				}
			} finally{
				if(stream!=null){
					stream.close();
				}
			}			
		}
	}

	@AfterThrowing(pointcut="exceptionPointcut()", throwing="throwable")
	public void handleException(final JoinPoint joinPoint, final Throwable throwable){
		UserException userException = null;
		Throwable loggedException = null;
		if(throwable instanceof UserException){
			userException = (UserException)throwable;
			loggedException = userException;
		}else{
			Throwable cause = getCause(throwable);
			loggedException = cause;
			userException = prepareUserException(joinPoint,cause);
		}
		
		UserExceptionThreadLocalHolder.setUserException(userException);
		
		if(FirstFailExceptionThreadLocalHolder.getThrowable()==null){
			FirstFailExceptionThreadLocalHolder.setThrowable(loggedException);
			final Object[] args = joinPoint.getArgs();
			final Signature signature = joinPoint.getSignature();
			final Object target = joinPoint.getTarget();
			String className = null;
			String methodName = null;
			if (target != null) {
				className = target.getClass().getName();			
			}
			if (signature != null) {
				methodName = signature.getName();
			}
			ExceptionReturnLogEvent exceptionReturnLogEvent = new ExceptionReturnLogEvent(
					className, methodName, args, throwable, userException.getErrorCode(), userException.getErrorMessage());
			logPublisher.publishLog(exceptionReturnLogEvent);
		}else{
			// as of now ignore multiple exception handling in the call chain
			// advance implementation can stack up the Throwable and the caller chain from JoinPoint in a List and process as needed
		}
		
		throw userException;
	}
	
	private Throwable getCause(Throwable throwable){
		Throwable cause = throwable;
		if(throwable instanceof SoftException){
			cause = throwable.getCause();
		}
		return cause;
	}
	
	private UserException prepareUserException(final JoinPoint joinPoint, final Throwable throwable){
		UserException userException = null;
		if(applicationConext!=null && customExceptionHandler == null){
			try{
				customExceptionHandler = applicationConext.getBean("customExceptionHandler", ExceptionHandler.class);
			}catch(final Exception exception){
				// ignore in case the bean is not present in the context, fall back to default implementation
			}
		}
		if(customExceptionHandler!=null){
			userException = customExceptionHandler.prepareUserException(joinPoint, throwable);
		}
		if(userException == null && defaultExceptionHandler!=null){
			userException = defaultExceptionHandler.prepareUserException(joinPoint, throwable);
		}
		return userException;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationConext = applicationContext;
	}
}
