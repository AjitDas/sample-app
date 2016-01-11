package com.capgemini.greet.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="greetRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GreetRequest {
	
	@XmlElement(name="greetMessage")
	private String greetMessage;
	
	public GreetRequest(){}

	@Override
	public String toString() {
		return "GreetRequest [greetMessage=" + greetMessage + "]";
	}

	/**
	 * @return the greetMessage
	 */
	public String getGreetMessage() {
		return greetMessage;
	}

	/**
	 * @param greetMessage the greetMessage to set
	 */
	public void setGreetMessage(String greetMessage) {
		this.greetMessage = greetMessage;
	}

}
