package com.capgemini.user.logging.aop;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.AuditCallLogEvent;
import com.capgemini.user.logging.event.AuditReturnLogEvent;

@Aspect @Order(2) @Component("auditLoggerAspect")
public class AuditLoggerAspect {

	@Autowired @Qualifier("logPublisher") 
	private LogPublisher logPublisher;
	
	@Pointcut("execution(@com.capgemini.user.logging.core.Auditable * *(..)) && within(com.capgemini.user..*) && !within(com.capgemini.user.logging..*) && !within(com.sun.proxy..*Proxy*)")
	public void auditPointcut(){}
	
	@PostConstruct
	protected void intialize(){}
	
	@Around("auditPointcut()")
	public Object auditLog(final ProceedingJoinPoint joinPoint) throws Throwable {
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

		String name = joinPoint.getStaticPart().getSignature().getDeclaringTypeName() + "." + joinPoint.getStaticPart().getSignature().getName();
		StopWatch stopWatch = new StopWatch(name);
		Date now = Calendar.getInstance().getTime();
		Object returnValue = null;
		final AuditCallLogEvent auditCallLogEvent = new AuditCallLogEvent(className, methodName, args,now);
		boolean exitThroughException = false;
		try {
			stopWatch.start(name);
			if (logPublisher != null) {
				logPublisher.publishLog(auditCallLogEvent);
			}
			returnValue = joinPoint.proceed();
			return returnValue;
		}
		catch (Throwable ex) {
			if(stopWatch.isRunning()) {
				stopWatch.stop();
			}
			exitThroughException = true;
			Date nowNow = Calendar.getInstance().getTime();
			String exceptionMsg = String.format("Exception of Type [%s] Thrown With Message [%s]",ex.getClass().getName(),ex.getMessage());
			final AuditReturnLogEvent audiReturnLogEvent = new AuditReturnLogEvent(className, methodName, args,exceptionMsg,now,nowNow,stopWatch.getTotalTimeMillis());
			if (logPublisher != null) {
				logPublisher.publishLog(audiReturnLogEvent);
			}
			throw ex;
		}
		finally {
			if (!exitThroughException) {
				if(stopWatch.isRunning()) {
					stopWatch.stop();
				}
				Date nowNow = Calendar.getInstance().getTime();
				final AuditReturnLogEvent audiReturnLogEvent = new AuditReturnLogEvent(className, methodName, args,returnValue,now,nowNow,stopWatch.getTotalTimeMillis());
				if (logPublisher != null) {
					logPublisher.publishLog(audiReturnLogEvent);
				}
			}
		}
	}
}
