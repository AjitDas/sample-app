package com.capgemini.user.service.dto.weather;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="NewDataSet")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NewDataSet")
public class CitiesByCountryDataSet {
	
	@XmlElement(name="Table")
	private List<CitiesByCountryData> countryCityData;

	/**
	 * @return the countryCityData
	 */
	public List<CitiesByCountryData> getCountryCityData() {
		if(countryCityData==null){
			countryCityData = new ArrayList<>();
		}
		return countryCityData;
	}

	/**
	 * @param countryCityData the countryCityData to set
	 */
	public void setCountryCityData(List<CitiesByCountryData> countryCityData) {
		this.countryCityData = countryCityData;
	}
}
