package com.ajit.common.logging.core;

import org.springframework.stereotype.Component;

import com.ajit.common.logging.event.LogEvent;

@Component("defaultLogEventStringConverter")
public class DefaultLogEventStringConverter implements LogEventStringConverter {

	@Override
	public String convertLogEventToString(LogEvent logEvent) {
		if(logEvent !=null){
			String logEventAsString = logEvent.toString();
			return postProcess(logEventAsString);
		}
		return null;
	}
	
	protected String postProcess(String logEventAsString){
		return logEventAsString;
	}

}
