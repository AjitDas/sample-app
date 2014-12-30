package com.capgemini.user.concurrency.core;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CallableCommand<T> implements Callable<T> {
	
	private Command<T> delegate;
	private CountDownLatch countDownLatch;
	private T result;
	
	public CallableCommand(Command<T> task){
		if(task==null){
			throw new IllegalArgumentException("Command can't be NULL");
		}
		this.delegate = task;
	}
	
	public CallableCommand(Command<T> task, CountDownLatch countDownLatch){
		if(task==null){
			throw new IllegalArgumentException("Command can't be NULL");
		}
		this.delegate = task;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public final T call() throws Exception {
		try{
			result = delegate.execute();
		}finally{
			if(countDownLatch!=null){
				countDownLatch.countDown();
			}
		}
		return result;
	}
	
	public T result(){
		return result;
	}
	
	public Command<T> delegate(){
		return delegate;
	}

}
