package com.capgemini.user.service.impl;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.ws.client.core.WebServiceTemplate;

import com.capgemini.user.concurrency.core.Command;
import com.capgemini.user.service.dto.CityWeatherData;
import com.capgemini.user.service.dto.weather.WeatherRequest;
import com.capgemini.user.service.dto.weather.WeatherResponse;

@NotThreadSafe
public class CityWeatherDataRetrieverCommand implements Command<CityWeatherData>{
	
	private WebServiceTemplate globalWeatherService;
	private WeatherRequest weatherRequest;
	
	public CityWeatherDataRetrieverCommand(WebServiceTemplate webServiceTemplate, WeatherRequest weatherRequest){
		this.globalWeatherService = webServiceTemplate;
		this.weatherRequest = weatherRequest;
	}

	@Override
	public CityWeatherData execute() {
		WeatherResponse weatherResponse = (WeatherResponse)globalWeatherService.marshalSendAndReceive(weatherRequest);
		CityWeatherData cityWeatherData = null;
		if(weatherResponse!=null){
			cityWeatherData = new CityWeatherData();
			cityWeatherData.setCity(weatherRequest.getCityName());
			if(weatherResponse.getWeatherCurrentData()!=null){
				cityWeatherData.setLocation(weatherResponse.getWeatherCurrentData().getLocation());
				cityWeatherData.setTimeRecorded(weatherResponse.getWeatherCurrentData().getTimeRecorded());
				cityWeatherData.setTemprature(weatherResponse.getWeatherCurrentData().getTemprature());
				cityWeatherData.setHumidity(weatherResponse.getWeatherCurrentData().getHumidity());
				cityWeatherData.setWindSpeed(weatherResponse.getWeatherCurrentData().getWindSpeed());
				cityWeatherData.setVisibility(weatherResponse.getWeatherCurrentData().getVisibility());
				cityWeatherData.setPressure(weatherResponse.getWeatherCurrentData().getPressure());
				cityWeatherData.setDewPoint(weatherResponse.getWeatherCurrentData().getDewPoint());
			}
		}
		return cityWeatherData;
	}
	

}
