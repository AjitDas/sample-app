package com.capgemini.user.service.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.user.service.WeatherService;
import com.capgemini.user.service.dto.CitiesByCountry;
import com.capgemini.user.service.dto.WeatherData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContextTest.xml")
public class WeatherServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WeatherServiceTest.class);
	
	@Autowired private WeatherService weatherService;
	
	@BeforeClass
	public static void setup(){
		System.setProperty("ENV_NAME", "TEST");
	}
	
	@Test
	public void getCitiesByCountry(){
		CitiesByCountry citiesByCountry = weatherService.getCitiesByCountry("India");
		Assert.assertNotNull(citiesByCountry);
		Assert.assertEquals("India",citiesByCountry.getCountry());
		Assert.assertTrue(!citiesByCountry.getCities().isEmpty());
		logger.debug("Cities of India :"+citiesByCountry.getCities());
	}
	
	@Test
	public void getCurrentWeatherData(){
		WeatherData weatherData = weatherService.getWeather("India", "Bhubaneswar");
		Assert.assertNotNull(weatherData);
		Assert.assertEquals("India", weatherData.getCountry());
		Assert.assertEquals("Bhubaneswar", weatherData.getCity());
		logger.debug("Current Weather Data :"+weatherData);
	}

}
