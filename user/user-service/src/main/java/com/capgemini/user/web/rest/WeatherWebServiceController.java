package com.capgemini.user.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.user.service.WeatherService;
import com.capgemini.user.service.dto.CitiesByCountry;
import com.capgemini.user.service.dto.WeatherData;

@Controller @RequestMapping("/weather")
public class WeatherWebServiceController {
	
	@Autowired
	private WeatherService weatherService;

	@ResponseBody @RequestMapping(value="/cities-by-country/{countryName}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<CitiesByCountry> getCitiesByCountry(@PathVariable("countryName") String countryName, Model model){
		if(countryName==null || countryName.isEmpty()){
			return new ResponseEntity<CitiesByCountry>(HttpStatus.BAD_REQUEST);
		}
		CitiesByCountry citiesByCountry = weatherService.getCitiesByCountry(countryName);
		return new ResponseEntity<CitiesByCountry>(citiesByCountry,HttpStatus.OK);
	}
	
	@ResponseBody @RequestMapping(value="/get-current-weather/{countryName}/{cityName}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<WeatherData> getWeatherByCountryAndCity(@PathVariable("countryName") String countryName,
			@PathVariable("cityName") String cityName, Model model){
		if(countryName==null || countryName.isEmpty() || cityName==null || cityName.isEmpty()){
			return new ResponseEntity<WeatherData>(HttpStatus.BAD_REQUEST);
		}
		WeatherData currentWeatherData = weatherService.getWeather(countryName, cityName);
		return new ResponseEntity<WeatherData>(currentWeatherData,HttpStatus.OK);
	}
}
