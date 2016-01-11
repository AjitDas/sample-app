package com.ajit.common.concurrency.core;

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
		Exception exceptionThrown = null;
		try{
			preProcess();
			result = delegate().execute();
		}catch(Exception exception){
			exceptionThrown = exception;
			throw exception;
		}finally{
			if(countDownLatch!=null){
				countDownLatch.countDown();
			}
			postProcess(result,exceptionThrown);
		}
		return result;
	}
	
	protected void preProcess(){}
	
	protected void postProcess(T result, Exception exception){}
	
	public T result(){
		return result;
	}
	
	public Command<T> delegate(){
		return delegate;
	}

}