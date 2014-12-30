package com.capgemini.user.logging.core;

import com.capgemini.user.logging.event.LogEvent;

public interface LogPublisher {

	public void publishLog(LogEvent logEvent);
}
