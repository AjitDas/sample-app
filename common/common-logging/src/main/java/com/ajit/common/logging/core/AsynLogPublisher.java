package com.ajit.common.logging.core;

import org.springframework.stereotype.Component;

import com.ajit.common.logging.event.LogEvent;

@Component("asynLogPublisher")
public class AsynLogPublisher implements LogPublisher{

	@Override
	public void publishLog(LogEvent logEvent) {
		
	}

}
