package com.ajit.common.exceptionhandling.core;

import org.aspectj.lang.JoinPoint;

import com.ajit.common.exceptionhandling.exception.CommonException;

public interface CommonExceptionHandler {

	public CommonException prepareCommonException(JoinPoint joinPoint, Throwable throwable);
}
