package com.ajit.common.logging.event;

public class CallLogEvent extends LogEvent{

	/** serial version UID */
	private static final long serialVersionUID = -2805399643221757369L;
	protected String className;
	protected String methodName;
	protected Object[] arguments;
	
	public CallLogEvent(String className,String methodName,Object[] arguments){
		this.className = className;
		this.methodName = methodName;
		this.arguments = arguments;
	}
	
	@Override
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append(super.stringfyPrefix());
		final StringBuilder msgBuilder = new StringBuilder();
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
		return "CallLogEvent";
	}
}
