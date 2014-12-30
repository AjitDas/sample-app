package com.capgemini.user.logging.core;

import com.capgemini.user.logging.event.LogEvent;

public interface LogEventStringConverter {
	
	public String convertLogEventToString(LogEvent logEvent);

}
