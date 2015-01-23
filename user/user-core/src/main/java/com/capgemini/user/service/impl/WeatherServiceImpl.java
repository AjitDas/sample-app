package com.capgemini.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.capgemini.user.concurrency.ConcurrencyProcessor;
import com.capgemini.user.service.WeatherService;
import com.capgemini.user.service.dto.AllCitiesWeatherData;
import com.capgemini.user.service.dto.CitiesByCountry;
import com.capgemini.user.service.dto.City;
import com.capgemini.user.service.dto.CityWeatherData;
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
	
	@Autowired
	private ConcurrencyProcessor concurrencyProcessor;

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
			CityWeatherData cityWeatherData = new CityWeatherData();
			cityWeatherData.setCity(cityName);
			if(weatherResponse.getWeatherCurrentData()!=null){
				cityWeatherData.setLocation(weatherResponse.getWeatherCurrentData().getLocation());
				cityWeatherData.setTimeRecorded(weatherResponse.getWeatherCurrentData().getTimeRecorded());
				cityWeatherData.setTemprature(weatherResponse.getWeatherCurrentData().getTemprature());
				cityWeatherData.setHumidity(weatherResponse.getWeatherCurrentData().getHumidity());
				cityWeatherData.setWindSpeed(weatherResponse.getWeatherCurrentData().getWindSpeed());
				cityWeatherData.setVisibility(weatherResponse.getWeatherCurrentData().getVisibility());
				cityWeatherData.setPressure(weatherResponse.getWeatherCurrentData().getPressure());
				cityWeatherData.setDewPoint(weatherResponse.getWeatherCurrentData().getDewPoint());
				weatherData.setStatus(weatherResponse.getWeatherCurrentData().getStatus());
			}
			weatherData.setCityWeatherData(cityWeatherData);
		}
		return weatherData;
	}

	@Override
	public AllCitiesWeatherData getAllCitiesWeather(String countryName) {
		if(countryName==null || countryName.trim().equals("")){
			throw new IllegalArgumentException("Argument countryName can't be either NULL or empty");
		}
		AllCitiesWeatherData allCitiesWeatherData = new AllCitiesWeatherData();
		CitiesByCountryRequest request = new CitiesByCountryRequest();
		request.setCountryName(countryName);
		CitiesByCountryResponse responseWs = (CitiesByCountryResponse)globalWeatherService.marshalSendAndReceive(request);
		List<CityWeatherDataRetrieverCommand> commandsToExecute = new ArrayList<>();
		if(responseWs!=null){
			List<CitiesByCountryData> cities = responseWs.getCitiesByCountryResult().getCountryCityData();
			if(cities!=null && !cities.isEmpty()){
				allCitiesWeatherData.setCountry(cities.get(0).getCountry());
				for(CitiesByCountryData city:cities){
					WeatherRequest weatherRequest = new WeatherRequest();
					weatherRequest.setCountryName(city.getCountry());
					weatherRequest.setCityName(city.getCity());
					commandsToExecute.add(new CityWeatherDataRetrieverCommand(globalWeatherService, weatherRequest));
				}
			}
		}
		
		List<CityWeatherData> citiesWeatherData = concurrencyProcessor.processAndWaitForComplete(commandsToExecute);
		for(CityWeatherData cityWeatherData:citiesWeatherData){
			allCitiesWeatherData.getCitiesWeatherData().add(cityWeatherData);
		}
		return allCitiesWeatherData;
	}

}
