package com.capgemini.user.exception.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.capgemini.user.service.util.MetadataHeaderThreadLocalHolder;
import com.capgemini.user.service.util.MetadataHeaderThreadLocalHolder.MetadataHeaders;

@XmlRootElement(name="errorResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {
	
	@XmlElement(name="errorCode", required=true)
	private String errorCode;
	
	@XmlElement(name="errorMessage")
	private String errorMessage;
	
	@XmlElement(name="requestedUrl")
	private String requestedUrl;
	
	@XmlElement(name="X-Correlation-Id")
	private String correlationId;
	
	@XmlElement(name="X-Message-Id")
	private String messageId;
	
	@XmlElement(name="X-System-Id")
	private String systemId;
	
	public ErrorResponse(){
		correlationId = MetadataHeaderThreadLocalHolder.getMetadatHeaderFromThreadLocal(MetadataHeaders.X_CORRELATION_ID);
		messageId = MetadataHeaderThreadLocalHolder.getMetadatHeaderFromThreadLocal(MetadataHeaders.X_MESSAGE_ID);
		systemId = MetadataHeaderThreadLocalHolder.getMetadatHeaderFromThreadLocal(MetadataHeaders.X_SYSTEM_ID);
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the requestedUrl
	 */
	public String getRequestedUrl() {
		return requestedUrl;
	}

	/**
	 * @param requestedUrl the requestedUrl to set
	 */
	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}

	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	
}
