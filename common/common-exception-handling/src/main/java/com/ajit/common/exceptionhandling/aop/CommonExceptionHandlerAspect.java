package com.ajit.common.exceptionhandling.aop;

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
import org.springframework.stereotype.Component;

import com.ajit.common.exceptionhandling.core.CommonExceptionHandler;
import com.ajit.common.exceptionhandling.exception.CommonException;
import com.ajit.common.exceptionhandling.logging.event.ExceptionReturnLogEvent;
import com.ajit.common.exceptionhandling.util.CommonExceptionThreadLocalHolder;
import com.ajit.common.exceptionhandling.util.FirstFailExceptionThreadLocalHolder;
import com.ajit.common.logging.core.LogPublisher;

@Aspect @Order(1) @Component("commonExceptionHandlerAspect")
public class CommonExceptionHandlerAspect implements ApplicationContextAware {

	private Properties errorMappingProps= new Properties();
	
	private ApplicationContext applicationConext;
	
	@Value("${app.name:common-exceptionhandling}")
	private String appname;

	@Value("${error.mapping.properties.file:default-error-mapping.properties}")
	private String errorMappingPropFile;

	@Autowired @Qualifier("logPublisher") 
	private LogPublisher logPublisher;
	
	@Autowired @Qualifier("defaultCommonExceptionHandler")
	private CommonExceptionHandler defaultCommonExceptionHandler;
	
	//@Autowired @Lazy @Qualifier("customExceptionHandler")
	private CommonExceptionHandler customExceptionHandler;
	
	//@Pointcut("execution(public * com.ajit..*(..) throws !com.ajit.common.exceptionhandling.exception.CommonException+) && within(com.ajit..*) && !within(com.ajit.common.exceptionhandling.aop..*) && !within(com.sun.proxy..*Proxy*)"
	//		+ "&& !within(com.ajit..*Test) && !within(com.ajit..Test*)")
	@Pointcut("execution(public * com.ajit..*(..) throws !com.ajit.common.exceptionhandling.exception.CommonException+) && within(com.ajit..*) && !within(com.ajit.common.exceptionhandling.aop..*) && !within(com.sun.proxy..*Proxy*)")
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
		CommonException commonException = null;
		Throwable loggedException = null;
		if(throwable instanceof CommonException){
			commonException = (CommonException)throwable;
			loggedException = commonException;
		}else{
			Throwable cause = getCause(throwable);
			loggedException = cause;
			commonException = prepareCommonException(joinPoint,cause);
		}
		
		CommonExceptionThreadLocalHolder.setCommonException(commonException);
		
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
					className, methodName, args, throwable, commonException.getErrorCode(), commonException.getErrorMessage());
			logPublisher.publishLog(exceptionReturnLogEvent);
		}else{
			// as of now ignore multiple exception handling in the call chain
			// advance implementation can stack up the Throwable and the caller chain from JoinPoint in a List and process as needed
		}
		
		throw commonException;
	}
	
	private Throwable getCause(Throwable throwable){
		Throwable cause = throwable;
		if(throwable instanceof SoftException){
			cause = throwable.getCause();
		}
		return cause;
	}
	
	private CommonException prepareCommonException(final JoinPoint joinPoint, final Throwable throwable){
		CommonException commonException = null;
		if(applicationConext!=null && customExceptionHandler == null){
			try{
				customExceptionHandler = applicationConext.getBean("customExceptionHandler", CommonExceptionHandler.class);
			}catch(final Exception exception){
				// ignore in case the bean is not present in the context, fall back to default implementation
			}
		}
		if(customExceptionHandler!=null){
			commonException = customExceptionHandler.prepareCommonException(joinPoint, throwable);
		}
		if(commonException == null && defaultCommonExceptionHandler!=null){
			commonException = defaultCommonExceptionHandler.prepareCommonException(joinPoint, throwable);
		}
		return commonException;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationConext = applicationContext;
	}
}
