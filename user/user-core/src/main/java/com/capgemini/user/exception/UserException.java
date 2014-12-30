package com.capgemini.user.exception;

public class UserException extends RuntimeException{
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String errorMessage;

	public UserException(){
		super();
	}
	
	public UserException(String message){
		super(message);
	}
	
	public UserException(Throwable cause){
		super(cause);
	}
	
	public UserException(String message, Throwable cause){
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
