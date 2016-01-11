package com.ajit.common.exceptionhandling.exception;

public class CommonException extends RuntimeException{
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String errorMessage;

	public CommonException(){
		super();
	}
	
	public CommonException(String message){
		super(message);
	}
	
	public CommonException(Throwable cause){
		super(cause);
	}
	
	public CommonException(String message, Throwable cause){
		super(message,cause);
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
