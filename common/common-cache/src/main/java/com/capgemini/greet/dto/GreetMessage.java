package com.capgemini.greet.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement(name="greetMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public class GreetMessage {
	
	@XmlElement(name="greeting")
	private String greetMessage;
	
	@XmlElement(name="time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd-HH:mm:SS.ZZZ", timezone="EST")
	private Date greetTime;
	
	public GreetMessage(){}

	@Override
	public String toString() {
		return "GreetMessage [greetMessage=" + greetMessage + ", greetTime="
				+ greetTime + "]";
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

	/**
	 * @return the greetTime
	 */
	public Date getGreetTime() {
		return greetTime;
	}

	/**
	 * @param greetTime the greetTime to set
	 */
	public void setGreetTime(Date greetTime) {
		this.greetTime = greetTime;
	}
	
	

}
