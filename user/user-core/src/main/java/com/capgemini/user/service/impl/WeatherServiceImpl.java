package com.capgemini.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.capgemini.user.service.WeatherService;
import com.capgemini.user.service.dto.CitiesByCountry;
import com.capgemini.user.service.dto.City;
import com.capgemini.user.service.dto.WeatherData;
import com.capgemini.user.service.dto.weather.CitiesByCountryRequest;
import com.capgemini.user.service.dto.weather.CitiesByCountryResponse;
import com.capgemini.user.service.dto.weather.CitiesByCountryData;
import com.capgemini.user.service.dto.weather.WeatherRequest;
import com.capgemini.user.service.dto.weather.WeatherResponse;

@Component("weatherService")
public class WeatherServiceImpl implements WeatherService{
	
	@Autowired @Qualifier("globalWebServiceTemplate")
	private WebServiceTemplate globalWeatherService;

	@Override
	public CitiesByCountry getCitiesByCountry(String countryName) {
		if(countryName==null || countryName.trim().equals("")){
			throw new IllegalArgumentException("Argument countryName can't be either NULL or empty");
		}
		CitiesByCountryRequest request = new CitiesByCountryRequest();
		request.setCountryName(countryName);
		CitiesByCountryResponse responseWs = (CitiesByCountryResponse)globalWeatherService.marshalSendAndReceive(request);
		CitiesByCountry response = new CitiesByCountry();
		if(responseWs!=null){
			List<CitiesByCountryData> dataSet = responseWs.getCitiesByCountryResult().getCountryCityData();
			if(dataSet!=null && !dataSet.isEmpty()){
				response.setCountry(countryName);
				for(CitiesByCountryData countryCity:dataSet){
					if(countryCity.getCountry().equalsIgnoreCase(countryName)){
						City city = new City();
						city.setName(countryCity.getCity());
						response.getCities().add(city);
					}
				}
			}
		}
		return response;
	}

	@Override
	public WeatherData getWeather(String countryName, String cityName) {
		if(countryName==null || countryName.trim().equals("")){
			throw new IllegalArgumentException("Argument countryName can't be either NULL or empty");
		}
		if(cityName==null || cityName.trim().equals("")){
			throw new IllegalArgumentException("Argument cityName can't be either NULL or empty");
		}
		WeatherRequest weatherRequest = new WeatherRequest();
		weatherRequest.setCountryName(countryName);
		weatherRequest.setCityName(cityName);
		WeatherResponse weatherResponse = (WeatherResponse)globalWeatherService.marshalSendAndReceive(weatherRequest);
		WeatherData weatherData = new WeatherData();
		if(weatherResponse!=null){
			weatherData.setCountry(countryName);
			weatherData.setCity(cityName);
			if(weatherResponse.getWeatherCurrentData()!=null){
				weatherData.setLocation(weatherResponse.getWeatherCurrentData().getLocation());
				weatherData.setTimeRecorded(weatherResponse.getWeatherCurrentData().getTimeRecorded());
				weatherData.setTemprature(weatherResponse.getWeatherCurrentData().getTemprature());
				weatherData.setHumidity(weatherResponse.getWeatherCurrentData().getHumidity());
				weatherData.setWindSpeed(weatherResponse.getWeatherCurrentData().getWindSpeed());
				weatherData.setVisibility(weatherResponse.getWeatherCurrentData().getVisibility());
				weatherData.setPressure(weatherResponse.getWeatherCurrentData().getPressure());
				weatherData.setDewPoint(weatherResponse.getWeatherCurrentData().getDewPoint());
				weatherData.setStatus(weatherResponse.getWeatherCurrentData().getStatus());
			}
		}
		return weatherData;
	}

}
