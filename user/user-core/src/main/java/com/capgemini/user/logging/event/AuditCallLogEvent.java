package com.capgemini.user.logging.event;

import java.util.Date;

public class AuditCallLogEvent extends CallLogEvent {
	
	/** serial version UID */
	private static final long serialVersionUID = 1942948082103952721L;
	private Date startTime;

	public AuditCallLogEvent(String className,String methodName,Object[] arguments,Date startTime){
		super(className,methodName,arguments);
		this.startTime = startTime;
	}
	
	@Override
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append(super.stringfyPrefix());
		final StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(String.format("Execution Start Time: %1$tY-%1$tm-%1$tdT%1$tH:%1$tM:%1$tS.%1$tL",startTime)).append(System.getProperty("line.separator"));
		msgBuilder.append("Message: ").append(String.format("Entering method '%s' of class [%s]",methodName,className));
		if (arguments != null) {
			msgBuilder.append(arguments.length > 1 ? " with Arguments: ":" with Argument: ").append(System.getProperty("line.separator"));
			for (Object argument : arguments) {
				if (argument == null) {
					msgBuilder.append("null").append(System.getProperty("line.separator"));
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
		sb.append(msgBuilder.toString());
		return sb.toString();
	}
	
	@Override
	protected String logType(){
		return "AuditCallLogEvent";
	}
	
	@Override
	protected String logEventType(){
		return LogEventTypes.DEBUG.toString();
	}
	
}
