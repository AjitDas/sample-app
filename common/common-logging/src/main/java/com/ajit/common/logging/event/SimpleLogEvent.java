package com.ajit.common.logging.event;


public class SimpleLogEvent extends LogEvent{

	/** serial version UID */
	private static final long serialVersionUID = -2611742875342294364L;
	private String message;
	
	public SimpleLogEvent(String message){
		super();
		this.message = message;
	}
	
	public SimpleLogEvent(String message, String logEventType){
		super();
		logEventType(logEventType);
		this.message = message;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("Message: ").append(message).append(System.getProperty("line.separator"));
		return sb.toString();
	}
	
	@Override
	protected String logType(){
		return "SimpleLogEvent";
	}
}
