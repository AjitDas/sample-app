package com.capgemini.user.concurrency.core;

public interface CommandExecutionCallback<T> {

	public void beforeExecute();
	
	public void afterExecute(T returnResult, Throwable throwable);
	
}
