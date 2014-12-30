package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="GetWeatherResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeatherResponse {

	@XmlElement(name="GetWeatherResult")
	@XmlJavaTypeAdapter(value=WeatherCDATAXmlAdaptor.class)
	private WeatherCurrentData weatherCurrentData;
	
	public WeatherResponse(){}

	/**
	 * @return the weatherCurrentData
	 */
	public WeatherCurrentData getWeatherCurrentData() {
		return weatherCurrentData;
	}

	/**
	 * @param weatherCurrentData the weatherCurrentData to set
	 */
	public void setWeatherCurrentData(WeatherCurrentData weatherCurrentData) {
		this.weatherCurrentData = weatherCurrentData;
	}
	
	
}
