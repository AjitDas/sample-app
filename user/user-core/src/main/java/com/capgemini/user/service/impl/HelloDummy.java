package com.capgemini.user.service.impl;

import org.springframework.stereotype.Component;

import com.capgemini.user.logging.core.AppContextSingleton;
import com.capgemini.user.logging.core.Auditable;
import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.SimpleLogEvent;

@Component("helloDummy")
public class HelloDummy {
	
	private static final LogPublisher logPublisher = AppContextSingleton.getInstance().getLogPublisher();

	@Auditable
	public void sayHello(){
		logPublisher.publishLog(new SimpleLogEvent("Hello Dummy Service Bean", "TRACE"));
	}
	
}
