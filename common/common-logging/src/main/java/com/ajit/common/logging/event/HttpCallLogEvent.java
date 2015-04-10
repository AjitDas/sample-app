package com.ajit.common.logging.event;

import java.util.Map;

public class HttpCallLogEvent extends LogEvent {

	/** serial version UID */
	private static final long serialVersionUID = -7906293943981486466L;
	private Map<String, Object> keyVal;

	public HttpCallLogEvent(Map<String,Object> keyVal) {
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
		return "HttpCallLogEvent";
	}

}
