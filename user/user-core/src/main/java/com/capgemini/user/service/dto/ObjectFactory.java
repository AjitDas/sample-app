package com.capgemini.user.service.dto;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory(){}

	public CitiesByCountry createCitiesByCountry(){
		return new CitiesByCountry();
	}

	public City createCity(){
		return new City();
	}

	public User createUser(){
		return new User();
	}
	
	public WeatherData createWeatherData(){
		return new WeatherData();
	}

}
