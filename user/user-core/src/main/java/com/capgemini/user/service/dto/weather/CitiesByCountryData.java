package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType(name="Table")
@XmlAccessorType(XmlAccessType.FIELD)
public class CitiesByCountryData {

	@XmlElement(name="Country")
	private String country;
	@XmlElement(name="City")
	private String city;
	
	public CitiesByCountryData(){}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
