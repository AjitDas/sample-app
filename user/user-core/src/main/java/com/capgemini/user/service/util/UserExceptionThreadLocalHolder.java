package com.capgemini.user.service.util;

import com.capgemini.user.exception.UserException;

public final class UserExceptionThreadLocalHolder{

	private static final ThreadLocal<UserException> EXCEPTION_THREADLOCAL_CONTEXT =  new ThreadLocal<>();
	
	private UserExceptionThreadLocalHolder() {
		// Empty constructor.
	}

	public static UserException getUserException() {
		return EXCEPTION_THREADLOCAL_CONTEXT.get();
	}

	public static void setUserException(final UserException exception) {
		EXCEPTION_THREADLOCAL_CONTEXT.set(exception);
	}

	public static void removeUserException() {
		EXCEPTION_THREADLOCAL_CONTEXT.remove();
	}
}
