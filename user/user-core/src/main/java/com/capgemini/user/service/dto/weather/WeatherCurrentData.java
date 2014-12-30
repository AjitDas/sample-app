package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CurrentWeather")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeatherCurrentData {

	@XmlElement(name="Location")
	private String location;
	
	@XmlElement(name="Time")
	private String timeRecorded;
	
	@XmlElement(name="Wind")
	private String windSpeed;
	
	@XmlElement(name="Visibility")
	private String visibility;
	
	@XmlElement(name="Temperature")
	private String temprature;
	
	@XmlElement(name="DewPoint")
	private String dewPoint;
	
	@XmlElement(name="RelativeHumidity")
	private String humidity;
	
	@XmlElement(name="Pressure")
	private String pressure;
	
	@XmlElement(name="Status")
	private String status;
	
	public WeatherCurrentData(){}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the timeRecorded
	 */
	public String getTimeRecorded() {
		return timeRecorded;
	}

	/**
	 * @param timeRecorded the timeRecorded to set
	 */
	public void setTimeRecorded(String timeRecorded) {
		this.timeRecorded = timeRecorded;
	}

	/**
	 * @return the windSpeed
	 */
	public String getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the temprature
	 */
	public String getTemprature() {
		return temprature;
	}

	/**
	 * @param temprature the temprature to set
	 */
	public void setTemprature(String temprature) {
		this.temprature = temprature;
	}

	/**
	 * @return the dewPoint
	 */
	public String getDewPoint() {
		return dewPoint;
	}

	/**
	 * @param dewPoint the dewPoint to set
	 */
	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	/**
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	/**
	 * @return the pressure
	 */
	public String getPressure() {
		return pressure;
	}

	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(String pressure) {
		this.pressure = pressure;
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
