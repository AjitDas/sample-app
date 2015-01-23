package com.capgemini.user.service.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="citiesByCountry")
@XmlAccessorType(XmlAccessType.FIELD)
public class CitiesByCountry {
	
	@XmlElement(name="country")
	private String country;
	
	@XmlElementWrapper(name="cities")
	@XmlElement(name="city")
	private List<City> cities;
	
	public CitiesByCountry(){}

	@Override
	public String toString() {
		return "CitiesByCountry [country=" + country + ", cities=" + cities
				+ "]";
	}
	
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
	 * @return the cities
	 */
	public List<City> getCities() {
		if(cities==null){
			cities = new ArrayList<>();
		}
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

}
