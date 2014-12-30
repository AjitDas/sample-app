package com.capgemini.user.logging.event;

import java.util.Map;

public class HttpReturnLogEvent extends LogEvent {

	/** serial vesion UID */
	private static final long serialVersionUID = -3115746523108217316L;
	private Map<String, Object> keyVal;

	public HttpReturnLogEvent(Map<String,Object> keyVal) {
		this.keyVal = keyVal;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		if(keyVal!=null){
			for(Map.Entry<String, Object> entry:keyVal.entrySet()){
				sb.append(String.format("%s: ",entry.getKey())).append(String.format("%s ",entry.getValue()));
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}
	
	//TODO : Need to modify to AUDIT instead of DEBUG
	@Override
	protected String logEventType(){
		return LogEventTypes.DEBUG.toString();
	}
	
	@Override
	protected String logType(){
		return "HttpReturnLogEvent";
	}

}
