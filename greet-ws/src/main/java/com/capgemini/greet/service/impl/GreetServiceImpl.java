package com.capgemini.greet.service.impl;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.capgemini.greet.dto.GreetMessage;
import com.capgemini.greet.dto.GreetRequest;
import com.capgemini.greet.exception.GreetCheckedException;
import com.capgemini.greet.exception.GreetUncheckedException;
import com.capgemini.greet.service.GreetService;

@Service("greetService")
public class GreetServiceImpl implements GreetService {

	@Override
	public GreetMessage greet() {
		GreetMessage greetMessage = new GreetMessage();
		greetMessage.setGreetMessage("Hello Welcome To Spring MVC World");
		greetMessage.setGreetTime(Calendar.getInstance().getTime());
		return greetMessage;
	}

	@Override
	public GreetMessage greet(String greetMessageStr) throws GreetCheckedException{
		throwCheckedException(greetMessageStr);
		GreetMessage greetMessage = new GreetMessage();
		greetMessage.setGreetMessage("Hello Welcome To Spring MVC World, Message For You "+ greetMessageStr);
		greetMessage.setGreetTime(Calendar.getInstance().getTime());
		return greetMessage;
	}

	@Override
	public GreetMessage greet(GreetRequest greetRequest) {
		GreetMessage greetMessage = new GreetMessage();
		greetMessage.setGreetMessage(greetRequest.getGreetMessage());
		greetMessage.setGreetTime(Calendar.getInstance().getTime());
		return greetMessage;
	}
	
	private void throwCheckedException(String greetMessageStr) throws GreetCheckedException {
		if(greetMessageStr.equalsIgnoreCase("ce")){
			throw new GreetCheckedException();
		}
		if(greetMessageStr.equalsIgnoreCase("re")){
			throw new GreetUncheckedException();
		}
	}

}
