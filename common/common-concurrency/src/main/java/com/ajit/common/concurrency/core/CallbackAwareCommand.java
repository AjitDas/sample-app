package com.ajit.common.concurrency.core;


public interface CallbackAwareCommand<T>  extends Command<T>{

	public void beforeExecute();
	
	public void afterExecute(T returnResult, Throwable throwable);
	
}
