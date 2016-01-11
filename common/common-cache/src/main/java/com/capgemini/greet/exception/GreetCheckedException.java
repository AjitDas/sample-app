package com.capgemini.greet.exception;

public class GreetCheckedException extends Exception{

	/** serial version uid */
	private static final long serialVersionUID = -5238778131007318833L;

	public GreetCheckedException(){
		super();
	}

	public GreetCheckedException(String message){
		super(message);
	}

	public GreetCheckedException(Throwable throwable){
		super(throwable);
	}

	public GreetCheckedException(String message, Throwable throwable){
		super(message, throwable);
	}
}
