package com.ajit.common.exceptionhandling.misc.test;

import java.util.ConcurrentModificationException;

import org.aspectj.lang.JoinPoint;

import com.ajit.common.exceptionhandling.core.CommonExceptionHandler;
import com.ajit.common.exceptionhandling.exception.CommonException;

public class SimpleCustomExceptionHandler implements CommonExceptionHandler{

	@Override
	public CommonException prepareCommonException(JoinPoint joinPoint, Throwable throwable) {
		// do your own custom processing
		// for example check the throwable type and joinpoint and decide what code and message to return

		CommonException commonException = null;
		if(joinPoint.getTarget().getClass().equals(ClassThrowingException.class) && throwable.getClass().equals(ConcurrentModificationException.class)){
			commonException = new CommonException(throwable.getMessage(), throwable);
			commonException.setErrorCode("2001");
			commonException.setErrorMessage("Custom Handler Invoked When ConcurrenModificationException Is Thrown");
		}
		
		return commonException;
	}

}
