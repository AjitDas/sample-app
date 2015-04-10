package com.ajit.common.logging.aop;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ajit.common.logging.core.LogPublisher;
import com.ajit.common.logging.event.CallLogEvent;
import com.ajit.common.logging.event.ReturnLogEvent;

@Aspect @Order(3) @Component("traceLoggerAspect")
public class TraceLoggerAspect implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	@Autowired @Qualifier("logPublisher") 
	private LogPublisher logPublisher;
	
	@Pointcut("execution(public * com.ajit..*(..)) && !within(com.ajit.common.logging..*) && !within(com.sun.proxy..*Proxy*)")
	public void tracePointcut(){}
	
	@PostConstruct
	public void initialize(){
		if(logPublisher == null){
			LogPublisher logPublisherFromAppConext = applicationContext.getBean("logPublisher", LogPublisher.class);
			logPublisher = logPublisherFromAppConext;
		}
	}

	@Around(value="tracePointcut()")
	public Object traceLog(final ProceedingJoinPoint joinPoint) throws Throwable {
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
		final CallLogEvent callLogEvent = new CallLogEvent(className, methodName, args);
		if (logPublisher != null) {
			logPublisher.publishLog(callLogEvent);
		}
		
		final Object returnObject = joinPoint.proceed();
		if (returnObject != null) {
			final ReturnLogEvent returnLogEvent =  new ReturnLogEvent(className, methodName, args, returnObject);
			if (logPublisher != null) {
				logPublisher.publishLog(returnLogEvent);
			}
		}
		return  returnObject;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
