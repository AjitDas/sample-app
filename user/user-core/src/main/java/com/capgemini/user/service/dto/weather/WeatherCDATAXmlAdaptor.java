package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.capgemini.user.service.util.JaxbUtil;

public class WeatherCDATAXmlAdaptor extends XmlAdapter<String, WeatherCurrentData>{

	@Override
	public String marshal(WeatherCurrentData weatherCurentData) throws Exception {
		if(weatherCurentData==null){
			return null;
		}
		String weatherCurrentDataXml = JaxbUtil.getSingleton().marshall(weatherCurentData);
		return "<![CDATA[" + weatherCurrentDataXml + "]]>";
	}

	@Override
	public WeatherCurrentData unmarshal(String weatherCurentDataXml) throws Exception {
		boolean doesContainCDATA = weatherCurentDataXml!=null && !weatherCurentDataXml.isEmpty() && weatherCurentDataXml.startsWith("<![CDATA[") && weatherCurentDataXml.endsWith("]]>");
		String xmlToUnmarshall = null;
		if(doesContainCDATA){
			xmlToUnmarshall = weatherCurentDataXml.substring(9, weatherCurentDataXml.length()-3);
		}else{
			xmlToUnmarshall = weatherCurentDataXml;
		}
		return JaxbUtil.getSingleton().unmarshall(xmlToUnmarshall, WeatherCurrentData.class);
	}

}
