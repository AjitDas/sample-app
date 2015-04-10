package com.ajit.common.aspects.misc;

import java.io.IOException;

public class ClassThrowingException {

	public void methodThrowingIOException(boolean throwException) throws IOException{
		if(throwException){
			throw new IOException("IOException thrown for testing soften aspect");
		}
		return;
	}
	
	public void methodThrowingCustomCheckedException(boolean throwException) throws CustomCheckedException{
		if(throwException){
			throw new CustomCheckedException("CustomCheckedException thrown for testing soften aspect");
		}
	}
	
	public void methodThrowingRuntimeException(boolean throwException){
		if(throwException){
			throw new IllegalStateException("IllegalStateException thrown for testing soften aspect");
		}
	}
	
	public void methodThrowingCustomRuntimeException(boolean throwException){
		if(throwException){
			throw new CustomRuntimeException("CustomRuntimeException thrown for testing soften aspect");
		}
	}
}

class CustomCheckedException extends Exception{

	/** default serial version uid */
	private static final long serialVersionUID = 1L;

	public CustomCheckedException(){
		super();
	}

	public CustomCheckedException(String message){
		super(message);
	}

}

class CustomRuntimeException extends RuntimeException{

	/** default serial version uid */
	private static final long serialVersionUID = 1L;

	public CustomRuntimeException(){
		super();
	}

	public CustomRuntimeException(String message){
		super(message);
	}

}