package com.capgemini.user.service.util;


public class FirstFailExceptionThreadLocalHolder{

	private static final ThreadLocal<Throwable> EXCEPTION_THREADLOCAL_CONTEXT = new ThreadLocal<>();

	private FirstFailExceptionThreadLocalHolder() {
		// Empty constructor.
	}
	
	public static Throwable getThrowable() {
		return EXCEPTION_THREADLOCAL_CONTEXT.get();
	}

	public static void setThrowable(final Throwable exception) {
		EXCEPTION_THREADLOCAL_CONTEXT.set(exception);
	}

	public static void removeThrowable() {
		EXCEPTION_THREADLOCAL_CONTEXT.remove();
	}
}
