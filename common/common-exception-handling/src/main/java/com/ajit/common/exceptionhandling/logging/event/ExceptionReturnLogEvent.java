package com.ajit.common.exceptionhandling.logging.event;

import org.aspectj.lang.SoftException;

import com.ajit.common.logging.event.LogEvent;
import com.ajit.common.logging.event.LogEventTypes;

public class ExceptionReturnLogEvent extends LogEvent{
	
	/** serial version UID */
	private static final long serialVersionUID = 988027122904526955L;
	
	protected String className;
	protected String methodName;
	protected Object[] arguments;
	protected Throwable throwable;
	protected String errorCode;
	protected String errorMessage;

	public ExceptionReturnLogEvent(String className, String methodName, Object[] arguments, Throwable throwable, String errorCode, String errorMessage){
		this.className = className;
		this.methodName = methodName;
		this.arguments = arguments;
		this.throwable = throwable;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append(super.stringfyPrefix());
		final StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append("Message: ").append(String.format("Exiting method '%s' of class [%s]",methodName,className));
		if (arguments != null) {
			msgBuilder.append(arguments.length > 1 ? " with Arguments: ":" with Argument: ").append(System.getProperty("line.separator"));
			for (Object argument : arguments) {
				if (argument == null) {
					msgBuilder.append("null");
				} else {
					msgBuilder.append("of Type ");
					msgBuilder.append("[");
					msgBuilder.append(argument.getClass().getName());
					msgBuilder.append("] ");
					msgBuilder.append("with Value '");
					msgBuilder.append(argument.toString());
					msgBuilder.append("'");
					msgBuilder.append(System.getProperty("line.separator"));
				}
			}
		}
		if(throwable!=null){
			final StringBuilder stackTraceBuilder = new StringBuilder();
			Throwable cause = null;
			if (throwable.getClass().equals(SoftException.class)) {
				cause = throwable.getCause();
			} else {
				cause = throwable;
			}
			final StackTraceElement[] traceElements = cause.getStackTrace();
			stackTraceBuilder.append(cause.getMessage());
			stackTraceBuilder.append(System.getProperty("line.separator"));
			for (StackTraceElement element : traceElements) {
				stackTraceBuilder.append(element);
				stackTraceBuilder.append(System.getProperty("line.separator"));
			}
			msgBuilder.append(" with Exception : ");
			msgBuilder.append("of Type ");
			msgBuilder.append("[");
			msgBuilder.append(cause.getClass().getName());
			msgBuilder.append("] ");
			msgBuilder.append(System.getProperty("line.separator"));
			msgBuilder.append(" Exception Code : '");
			msgBuilder.append(errorCode);
			msgBuilder.append("'");
			msgBuilder.append(System.getProperty("line.separator"));
			msgBuilder.append(" Exception Message : '");
			msgBuilder.append(errorMessage);
			msgBuilder.append("'");
			msgBuilder.append(System.getProperty("line.separator"));
			msgBuilder.append("Stacktrace : ");
			msgBuilder.append(stackTraceBuilder.toString());
			msgBuilder.append(System.getProperty("line.separator"));
		}
		sb.append(msgBuilder.toString());
		return sb.toString();
	}


	@Override
	protected String logType(){
		return "ExceptionReturnLogEvent";
	}

	@Override
	protected String logEventType(){
		return LogEventTypes.ERROR.toString();
	}
}
