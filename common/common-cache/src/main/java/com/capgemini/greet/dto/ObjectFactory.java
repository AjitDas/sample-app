package com.capgemini.greet.dto;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory(){}

	public GreetMessage createGreetMessage(){
		return new GreetMessage();
	}
	
	public GreetRequest createGreetRequest(){
		return new GreetRequest();
	}
}
