package com.capgemini.greet.service;

import com.capgemini.greet.dto.GreetMessage;
import com.capgemini.greet.dto.GreetRequest;
import com.capgemini.greet.exception.GreetCheckedException;

public interface GreetService {

	public GreetMessage greet();
	
	public GreetMessage greet(String greetMessage) throws GreetCheckedException;
	
	public GreetMessage greet(GreetRequest greetRequest);
}
