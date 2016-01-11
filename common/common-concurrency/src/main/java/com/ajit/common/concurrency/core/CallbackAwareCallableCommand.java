package com.ajit.common.concurrency.core;

import java.util.concurrent.CountDownLatch;

public class CallbackAwareCallableCommand<T> extends CallableCommand<T>{
	
	private CallbackAwareCommand<T> delegate;
	
	public CallbackAwareCallableCommand(CallbackAwareCommand<T> task){
		super(task);
		this.delegate = task;
	}
	
	public CallbackAwareCallableCommand(CallbackAwareCommand<T> task, CountDownLatch countDownLatch){
		super(task,countDownLatch);
		this.delegate = task;
	}
	
	@Override
	protected void preProcess(){
		delegate().beforeExecute();
	}
	
	@Override
	protected void postProcess(T result, Exception exception){
		delegate().afterExecute(result, exception);
	}
	
	@Override
	public CallbackAwareCommand<T> delegate(){
		return this.delegate;
	}

}