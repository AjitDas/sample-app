package com.capgemini.greet.exception;

public class GreetUncheckedException extends RuntimeException{

	/** serial version uid */
	private static final long serialVersionUID = 186549184789724444L;

	public GreetUncheckedException(){
		super();
	}

	public GreetUncheckedException(String message){
		super(message);
	}

	public GreetUncheckedException(Throwable throwable){
		super(throwable);
	}

	public GreetUncheckedException(String message, Throwable throwable){
		super(message, throwable);
	}
}
