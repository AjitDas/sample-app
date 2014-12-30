package com.capgemini.user.exception.core;

import org.aspectj.lang.JoinPoint;

import com.capgemini.user.exception.UserException;

public interface ExceptionHandler {

	public UserException prepareUserException(JoinPoint joinPoint, Throwable throwable);
}
