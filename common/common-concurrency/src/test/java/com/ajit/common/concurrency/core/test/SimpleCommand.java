package com.ajit.common.concurrency.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajit.common.concurrency.core.Command;

public class SimpleCommand implements Command<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleCommand.class);
	
	private String uuid;
	
	public SimpleCommand(String uuid){
		this.uuid = uuid;
	}

	@Override
	public String execute() {
		logger.debug(String.format("Running in Thread %s with UUID %s",Thread.currentThread(),uuid));
		Thread.sleep(100);
		return uuid;
	}

}
