package com.capgemini.user.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="currentWeather")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeatherData {

	@XmlElement(name="country")
	private String country;
	
	@XmlElement(name="cityWeather")
	private CityWeatherData cityWeatherData;
	
	@XmlElement(name="status")
	private String status;
	
	public WeatherData(){}

	@Override
	public String toString() {
		return "WeatherData [country=" + country + ", cityWeather=" + cityWeatherData + ", status=" + status + "]";
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
	 * @return the cityWeatherData
	 */
	public CityWeatherData getCityWeatherData() {
		return cityWeatherData;
	}

	/**
	 * @param cityWeatherData the cityWeatherData to set
	 */
	public void setCityWeatherData(CityWeatherData cityWeatherData) {
		this.cityWeatherData = cityWeatherData;
	}

}
