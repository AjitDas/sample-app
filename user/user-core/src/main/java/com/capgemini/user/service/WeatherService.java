package com.capgemini.user.service;

import com.capgemini.user.service.dto.AllCitiesWeatherData;
import com.capgemini.user.service.dto.CitiesByCountry;
import com.capgemini.user.service.dto.WeatherData;

public interface WeatherService {
	
	public CitiesByCountry getCitiesByCountry(String countryName);
	
	public WeatherData getWeather(String countryName, String cityName);
	
	public AllCitiesWeatherData getAllCitiesWeather(String countryName);

}
