package com.ajit.common.logging.event;

import java.util.Date;

public class AuditReturnLogEvent extends ReturnLogEvent{
	
	/** serial version UID */
	private static final long serialVersionUID = 7593809027661522545L;
	private Date startTime;
	private Date endTime;
	private long timeTakenMilliSecs;
	
	public AuditReturnLogEvent(String className, String methodName, Object[] arguments, Object returnValue, Date startTime, Date endTime, long timeTakenMilliSecs){
		super(className, methodName, arguments, returnValue);
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeTakenMilliSecs = timeTakenMilliSecs;
	}
	
	@Override
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append(super.stringfyPrefix());
		final StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(String.format("Execution Start Time: %1$tY-%1$tm-%1$tdT%1$tH:%1$tM:%1$tS.%1$tL",startTime)).append(System.getProperty("line.separator"));
		msgBuilder.append(String.format("Execution End Time: %1$tY-%1$tm-%1$tdT%1$tH:%1$tM:%1$tS.%1$tL",endTime)).append(System.getProperty("line.separator"));
		msgBuilder.append(String.format("Total Time taken in MilliSecond(s): %d",timeTakenMilliSecs)).append(System.getProperty("line.separator"));
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
		if(returnValue!=null){
			msgBuilder.append(" with Return : ");
			msgBuilder.append("of Type ");
			msgBuilder.append("[");
			msgBuilder.append(returnValue.getClass().getName());
			msgBuilder.append("] ");
			msgBuilder.append("with Value '");
			msgBuilder.append(returnValue.toString());
			msgBuilder.append("'");
			msgBuilder.append(System.getProperty("line.separator"));
		}else{
			msgBuilder.append(" with Return : null ").append(System.getProperty("line.separator"));
		}
		sb.append(msgBuilder.toString());
		return sb.toString();
	}

	@Override
	protected String logEventType() {
		return LogEventTypes.DEBUG.toString();
	}
	
	@Override
	protected String logType() {
		return "AuditReturnLogEvent";
	}

}
