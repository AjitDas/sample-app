package com.capgemini.user.util.test;

import java.util.ArrayList;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Test;

import com.capgemini.user.service.dto.weather.CitiesByCountryResponse;
import com.capgemini.user.service.dto.weather.CitiesByCountryData;
import com.capgemini.user.service.dto.weather.CitiesByCountryDataSet;
import com.capgemini.user.service.util.JaxbUtil;

public class JaxbUtilTest {
	
	private String expectedXml = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<web:GetCitiesByCountryResponse xmlns:web=\"http://www.webserviceX.NET\">"+
			    "<web:GetCitiesByCountryResult><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
			        "<web:NewDataSet>"+
			            "<web:Table>"+
			                "<web:Country>India</web:Country>"+
			                "<web:City>Bangalore</web:City>"+
			            "</web:Table>"+
			            "<web:Table>"+
			                "<web:Country>India</web:Country>"+
			                "<web:City>Bhubaneswar</web:City>"+
			            "</web:Table>"+
			        "</web:NewDataSet>]]>"+
			    "</web:GetCitiesByCountryResult>"+
			"</web:GetCitiesByCountryResponse>";
	
	private String xmlToUnmarshall = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<web:GetCitiesByCountryResponse xmlns:web=\"http://www.webserviceX.NET\">"+
			    "<web:GetCitiesByCountryResult><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
			        "<web:NewDataSet xmlns:web=\"http://www.webserviceX.NET\">"+
			            "<web:Table>"+
			                "<web:Country>India</web:Country>"+
			                "<web:City>Bangalore</web:City>"+
			            "</web:Table>"+
			            "<web:Table>"+
			                "<web:Country>India</web:Country>"+
			                "<web:City>Bhubaneswar</web:City>"+
			            "</web:Table>"+
			        "</web:NewDataSet>]]>"+
			    "</web:GetCitiesByCountryResult>"+
			"</web:GetCitiesByCountryResponse>";
	
	private String xmlToUnmarshallWithoutNamespace = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<web:GetCitiesByCountryResponse xmlns:web=\"http://www.webserviceX.NET\">"+
			    "<web:GetCitiesByCountryResult><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
			        "<NewDataSet>"+
			            "<Table>"+
			                "<Country>India</Country>"+
			                "<City>Bangalore</City>"+
			            "</Table>"+
			            "<Table>"+
			                "<Country>India</Country>"+
			                "<City>Bhubaneswar</City>"+
			            "</Table>"+
			        "</NewDataSet>]]>"+
			    "</web:GetCitiesByCountryResult>"+
			"</web:GetCitiesByCountryResponse>";
	
	@Test
	public void marshall(){
		
		CitiesByCountryResponse citiesByCountryResponse = new CitiesByCountryResponse();
		CitiesByCountryDataSet countryCityDataSet = new CitiesByCountryDataSet();
		List<CitiesByCountryData> countryCityData = new ArrayList<>();
		CitiesByCountryData countryCityDataItem1 = new CitiesByCountryData();
		countryCityDataItem1.setCountry("India");
		countryCityDataItem1.setCity("Bangalore");
		CitiesByCountryData countryCityDataItem2 = new CitiesByCountryData();
		countryCityDataItem2.setCountry("India");
		countryCityDataItem2.setCity("Bhubaneswar");
		countryCityData.add(countryCityDataItem1);
		countryCityData.add(countryCityDataItem2);
		countryCityDataSet.setCountryCityData(countryCityData);
		citiesByCountryResponse.setCitiesByCountryResult(countryCityDataSet); 
		
		String xmlString = JaxbUtil.getSingleton().marshall(citiesByCountryResponse);
		System.out.println(xmlString);
		Assert.assertNotNull(xmlString);
		
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
		
		DetailedDiff detailXmlDiff = new DetailedDiff(XMLUnit.compareXML(xmlString, expectedXml));
		@SuppressWarnings("unused")
		List<?> allDiffs = detailXmlDiff.getAllDifferences();
		//Assert.assertEquals("Difference Found "+detailXmlDiff.toString(), 0, allDiffs.size());
	}
	
	@Test
	public void unmarshall(){
		CitiesByCountryResponse response = JaxbUtil.getSingleton().unmarshall(xmlToUnmarshall, CitiesByCountryResponse.class);
		Assert.assertEquals(2,response.getCitiesByCountryResult().getCountryCityData().size());
		Assert.assertEquals("India",response.getCitiesByCountryResult().getCountryCityData().get(0).getCountry());
		Assert.assertEquals("India",response.getCitiesByCountryResult().getCountryCityData().get(0).getCountry());
		
		Assert.assertEquals("Bangalore",response.getCitiesByCountryResult().getCountryCityData().get(0).getCity());
		Assert.assertEquals("Bhubaneswar",response.getCitiesByCountryResult().getCountryCityData().get(1).getCity());
	}
	
	@Test
	public void unmarshallWithoutNamespace(){
		CitiesByCountryResponse response = JaxbUtil.getSingleton().unmarshall(xmlToUnmarshallWithoutNamespace, CitiesByCountryResponse.class);
		Assert.assertEquals(2,response.getCitiesByCountryResult().getCountryCityData().size());
		Assert.assertEquals("India",response.getCitiesByCountryResult().getCountryCityData().get(0).getCountry());
		Assert.assertEquals("India",response.getCitiesByCountryResult().getCountryCityData().get(0).getCountry());
		
		Assert.assertEquals("Bangalore",response.getCitiesByCountryResult().getCountryCityData().get(0).getCity());
		Assert.assertEquals("Bhubaneswar",response.getCitiesByCountryResult().getCountryCityData().get(1).getCity());
	}

}
