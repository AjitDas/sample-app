package com.ajit.common.concurrency.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajit.common.concurrency.core.CallbackAwareCommand;

public class SimpleCallbackAwareCommand implements CallbackAwareCommand<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleCallbackAwareCommand.class);
	
	private String uuid;
	
	public SimpleCallbackAwareCommand(String uuid){
		this.uuid = uuid;
	}

	@Override
	public String execute() {
		logger.debug(String.format("Running in Thread %s with UUID %s",Thread.currentThread(),uuid));
		Thread.sleep(100);
		return uuid;
	}

	@Override
	public void beforeExecute() {
		// do things before thread execution
		logger.debug(String.format("Pre-processing before running Thread %s with UUID %s",Thread.currentThread(),uuid));
	}

	@Override
	public void afterExecute(String returnResult, Throwable throwable) {
		// do things after  thread execution
		logger.debug(String.format("Post-processing after running Thread %s with UUID %s : return result %s , exception %s",
				Thread.currentThread(),uuid, returnResult, throwable));
	}

}
