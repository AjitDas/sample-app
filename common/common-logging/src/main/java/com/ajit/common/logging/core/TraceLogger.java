package com.ajit.common.logging.core;

import com.ajit.common.logging.event.LogEvent;

public interface TraceLogger {
	
	public void log(LogEvent logEvent);

}
