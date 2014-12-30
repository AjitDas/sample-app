package com.capgemini.user.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="city")
@XmlAccessorType(XmlAccessType.FIELD)
public class City {

	@XmlElement(name="name")
	private String name;
	
	public City(){}
	
	@Override
	public String toString() {
		return "City [name=" + name + "]";
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
