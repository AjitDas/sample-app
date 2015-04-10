package com.ajit.common.logging.core;

import com.ajit.common.logging.event.LogEvent;

public interface LogPublisher {

	public void publishLog(LogEvent logEvent);
}
