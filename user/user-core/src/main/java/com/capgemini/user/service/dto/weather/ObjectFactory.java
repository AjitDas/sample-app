package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory(){}

	public CitiesByCountryRequest createCitiesByCountryRequest(){
		return new CitiesByCountryRequest();
	}

	public CitiesByCountryResponse createCitiesByCountryResponse(){
		return new CitiesByCountryResponse();
	}

	public CitiesByCountryResult createCitiesByCountryResult(){
		return new CitiesByCountryResult();
	}

	public CitiesByCountryData createCitiesByCountryData(){
		return new CitiesByCountryData();
	}

	public CitiesByCountryDataSet createCitiesByCountryDataSet(){
		return new CitiesByCountryDataSet();
	}
	
	public WeatherRequest createWeatherRequest(){
		return new WeatherRequest();
	}
	
	public WeatherResponse createWeatherResponse(){
		return new WeatherResponse();
	}
	
	public WeatherCurrentData createWeatherCurrentData(){
		return new WeatherCurrentData();
	}
}
