package com.capgemini.user.logging.core;

import org.springframework.stereotype.Component;

import com.capgemini.user.logging.event.LogEvent;

@Component("asynLogPublisher")
public class AsynLogPublisher implements LogPublisher{

	@Override
	public void publishLog(LogEvent logEvent) {
		
	}

}
