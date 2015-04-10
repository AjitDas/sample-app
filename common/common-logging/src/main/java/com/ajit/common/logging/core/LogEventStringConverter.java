package com.ajit.common.logging.core;

import com.ajit.common.logging.event.LogEvent;

public interface LogEventStringConverter {
	
	public String convertLogEventToString(LogEvent logEvent);

}
