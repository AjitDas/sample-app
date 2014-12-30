package com.capgemini.user.service.dto.weather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlRootElement(name="GetCitiesByCountryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CitiesByCountryResponse {

	@XmlElement(name="GetCitiesByCountryResult")
	@XmlJavaTypeAdapter(value=CitiesCDATAXmlAdaptor.class)
	private CitiesByCountryDataSet citiesByCountryResult;
	
	public CitiesByCountryResponse(){}

	/**
	 * @return the getCitiesByCountryResult
	 */
	public CitiesByCountryDataSet getCitiesByCountryResult() {
		return citiesByCountryResult;
	}

	/**
	 * @param citiesByCountryResult the citiesByCountryResult to set
	 */
	public void setCitiesByCountryResult(
			CitiesByCountryDataSet citiesByCountryResult) {
		this.citiesByCountryResult = citiesByCountryResult;
	}
	
	
}
