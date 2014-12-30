package com.capgemini.user.concurrency.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.user.concurrency.core.Command;
import com.capgemini.user.concurrency.core.CommandExecutionCallback;

public class SimpleCommand implements Command<String> , CommandExecutionCallback<String>{
	
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

	@Override
	public void beforeExecute() {
		// do things before thread execution
	}

	@Override
	public void afterExecute(String returnResult, Throwable throwable) {
		// do things after  thread execution
	}

}
