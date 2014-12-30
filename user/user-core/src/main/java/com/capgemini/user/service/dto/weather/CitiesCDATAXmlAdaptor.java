package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.capgemini.user.service.util.JaxbUtil;

public class CitiesCDATAXmlAdaptor extends XmlAdapter<String, CitiesByCountryDataSet>{

	@Override
	public String marshal(CitiesByCountryDataSet countryCityDataSet) throws Exception {
		if(countryCityDataSet==null){
			return null;
		}
		String countryCityXml = JaxbUtil.getSingleton().marshall(countryCityDataSet);
		return "<![CDATA[" + countryCityXml + "]]>";
	}

	@Override
	public CitiesByCountryDataSet unmarshal(String countryCityXml) throws Exception {
		boolean doesContainCDATA = countryCityXml!=null && !countryCityXml.isEmpty() && countryCityXml.startsWith("<![CDATA[") && countryCityXml.endsWith("]]>");
		String xmlToUnmarshall = null;
		if(doesContainCDATA){
			xmlToUnmarshall = countryCityXml.substring(9, countryCityXml.length()-3);
		}else{
			xmlToUnmarshall = countryCityXml;
		}
		return JaxbUtil.getSingleton().unmarshall(xmlToUnmarshall, CitiesByCountryDataSet.class);
	}

}
