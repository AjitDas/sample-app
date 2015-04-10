package com.ajit.common.logging.event;

public class ReturnLogEvent extends LogEvent{

	/** serial version UID */
	private static final long serialVersionUID = -2805399643221757369L;
	protected String className;
	protected String methodName;
	protected Object[] arguments;
	protected Object returnValue;

	public ReturnLogEvent(String className, String methodName, Object[] arguments, Object returnValue){
		this.className = className;
		this.methodName = methodName;
		this.arguments = arguments;
		this.returnValue = returnValue;
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
	protected String logType(){
		return "ReturnLogEvent";
	}
}

