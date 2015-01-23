package com.capgemini.user.service.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="allCitiesWeather")
@XmlAccessorType(XmlAccessType.FIELD)
public class AllCitiesWeatherData {

	@XmlElement(name="country")
	private String country;
	
	@XmlElementWrapper(name="citiesWeather")
	@XmlElement(name="cityWeather")
	private List<CityWeatherData> citiesWeatherData;
	
	@XmlElement(name="status")
	private String status;
	
	public AllCitiesWeatherData(){}
	
	@Override
	public String toString() {
		return "AllCitiesWeatherData [country=" + country + ", citiesWeatherData=" + citiesWeatherData + ", status=" + status + "]";
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
	 * @return the citiesWeatherData
	 */
	public List<CityWeatherData> getCitiesWeatherData() {
		if(citiesWeatherData==null){
			citiesWeatherData = new ArrayList<>();
		}
		return citiesWeatherData;
	}

	/**
	 * @param citiesWeatherData the citiesWeatherData to set
	 */
	public void setCitiesWeatherData(List<CityWeatherData> citiesWeatherData) {
		this.citiesWeatherData = citiesWeatherData;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
