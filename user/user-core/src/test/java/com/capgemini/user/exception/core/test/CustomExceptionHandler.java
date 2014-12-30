package com.capgemini.user.exception.core.test;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import com.capgemini.user.exception.UserException;
import com.capgemini.user.exception.core.ExceptionHandler;
@Component("customExceptionHandler")
public class CustomExceptionHandler implements ExceptionHandler{

	@Override
	public UserException prepareUserException(final JoinPoint joinPoint,final Throwable throwable) {
		
		// do your own custom processing
		// for example check the throwable type and joinpoint and decide what code and message to return
		
		UserException userException = null;
		if(throwable.getClass().equals(IllegalArgumentException.class)){
			userException = new UserException(throwable.getMessage(), throwable);
			userException.setErrorCode("2001");
			userException.setErrorMessage("Custom IllegalArgumentException Message");
		}
		return userException;
	}

}
