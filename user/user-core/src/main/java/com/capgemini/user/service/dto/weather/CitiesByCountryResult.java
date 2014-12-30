package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType(name="GetCitiesByCountryResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class CitiesByCountryResult {
	
	@XmlElement(name="NewDataSet")
	private CitiesByCountryDataSet countryCityDataSet;
	
	public CitiesByCountryResult(){}

	/**
	 * @return the countryCityDataSet
	 */
	public CitiesByCountryDataSet getCountryCityDataSet() {
		return countryCityDataSet;
	}

	/**
	 * @param countryCityDataSet the countryCityDataSet to set
	 */
	public void setCountryCityDataSet(CitiesByCountryDataSet countryCityDataSet) {
		this.countryCityDataSet = countryCityDataSet;
	}
}
