package com.ajit.common.exceptionhandling.util;

import com.ajit.common.exceptionhandling.exception.CommonException;

public final class CommonExceptionThreadLocalHolder{

	private static final ThreadLocal<CommonException> EXCEPTION_THREADLOCAL_CONTEXT =  new ThreadLocal<>();
	
	private CommonExceptionThreadLocalHolder() {
		// Empty constructor.
	}

	public static CommonException getCommonException() {
		return EXCEPTION_THREADLOCAL_CONTEXT.get();
	}

	public static void setCommonException(final CommonException exception) {
		EXCEPTION_THREADLOCAL_CONTEXT.set(exception);
	}

	public static void removeCommonException() {
		EXCEPTION_THREADLOCAL_CONTEXT.remove();
	}
}
