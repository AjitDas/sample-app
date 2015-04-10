package com.ajit.common.logging.event;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.ajit.common.logging.util.MetadataHeaderThreadLocalHolder;
import com.ajit.common.logging.util.MetadataHeaderThreadLocalHolder.MetadataHeaders;

public class LogEvent implements Serializable{

	/** serial version UID */
	private static final long serialVersionUID = 9161210974199879893L;
	private final String logId;
	private final Date logCreated;
	private final String correlationId;
	private final String messageId;
	private final String systemId;
	
	
	@SuppressWarnings("unused")
	private String applicationName;	// kept for future enhancement - extra info other than X-System-Id can be used specific for an application
	private String logType;
	private LogEventTypes logEventType = LogEventTypes.DEBUG;
	
	public LogEvent(){
		logId = UUID.randomUUID().toString();
		logCreated = Calendar.getInstance().getTime();
		correlationId = MetadataHeaderThreadLocalHolder.getMetadatHeaderFromThreadLocal(MetadataHeaders.X_CORRELATION_ID);
		messageId = MetadataHeaderThreadLocalHolder.getMetadatHeaderFromThreadLocal(MetadataHeaders.X_MESSAGE_ID);
		systemId = MetadataHeaderThreadLocalHolder.getMetadatHeaderFromThreadLocal(MetadataHeaders.X_SYSTEM_ID);
	}
	
	@Override
	public String toString(){
		return this.stringfyPrefix();
	}
	
	protected String stringfyPrefix(){
		StringBuilder sb = new StringBuilder();
		sb.append(logType()).append(System.getProperty("line.separator"));
		sb.append("X-Log-Id: %s").append(System.getProperty("line.separator"));
		sb.append("X-Correlation-Id: %s").append(System.getProperty("line.separator"));
		sb.append("X-Message-Id: %s").append(System.getProperty("line.separator"));
		sb.append("X-System-Id: %s").append(System.getProperty("line.separator"));
		sb.append("Log Created Timestamp: %s").append(System.getProperty("line.separator"));
		sb.append("Log Level: %s").append(System.getProperty("line.separator"));
		return String.format(sb.toString(),logId,correlationId,messageId,systemId,logCreated,logEventType());
	}
	
	protected String logEventType(){
		return logEventType.toString();
	}
	
	protected void logEventType(String logEventType){
		if(logEventType!=null && LogEventTypes.valueOf(logEventType)!=null){
			this.logEventType = LogEventTypes.valueOf(logEventType);
		}else{
			this.logEventType = LogEventTypes.TRACE;
		}
	}
	
	protected String logType(){
		return this.logType == null ? "LogEvent" : this.logType;
	}
	
	public void logType(String logType){
		this.logType=logType;
	}
}
