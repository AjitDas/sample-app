package com.capgemini.user.service.impl;

import org.springframework.stereotype.Component;

import com.capgemini.user.logging.core.AppContextSingleton;
import com.capgemini.user.logging.core.Auditable;
import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.SimpleLogEvent;

@Component("miscDummyService")
public class MiscDummyService {
	
	private static final LogPublisher logPublisher = AppContextSingleton.getInstance().getLogPublisher();

	public void greetHelloWithLog(){
		logPublisher.publishLog(new SimpleLogEvent("Hello Service Bean Log", "TRACE"));
	}
	
	@Auditable
	public void greetHelloWithLogAlongWithAudit(){
		logPublisher.publishLog(new SimpleLogEvent("Hello Service Bean Audit Log", "TRACE"));
	}
}
