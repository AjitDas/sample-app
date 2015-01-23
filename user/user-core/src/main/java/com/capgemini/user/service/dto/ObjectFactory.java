package com.capgemini.user.service.dto;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory(){}

	public AllCitiesWeatherData createAllCitiesWeatherData(){
		return new AllCitiesWeatherData();
	}
	
	public CitiesByCountry createCitiesByCountry(){
		return new CitiesByCountry();
	}
	
	public City createCity(){
		return new City();
	}

	public CityWeatherData createCityWeatherData(){
		return new CityWeatherData();
	}
	
	public User createUser(){
		return new User();
	}
	
	public WeatherData createWeatherData(){
		return new WeatherData();
	}
}
