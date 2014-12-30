package com.capgemini.user.logging.core;

import com.capgemini.user.logging.event.LogEvent;

public interface TraceLogger {
	
	public void log(LogEvent logEvent);

}
