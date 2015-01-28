package com.capgemini.user.webservice.interceptor;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.HttpCallLogEvent;
import com.capgemini.user.logging.event.HttpReturnLogEvent;

@Component("clientInOutboundLogInterceptor")
public class ClientInOutboundLogInterceptor implements ClientInterceptor {
	
	@Autowired @Qualifier("logPublisher") 
	private LogPublisher logPublisher;
	
	/** toggle flags for enabling and disabling the outbound request, response and soapfault */
	
	@Value("${client.outbound.request.log:true}")
	private boolean logRequest;
	
	@Value("${client.outbound.response.log:true}")
	private boolean logResponse;
	
	@Value("${client.outbound.soapfault.log:true}")
	private boolean logFault;
	
	private TransformerFactory transformerFactory;
	
	@PostConstruct
	protected void initialize(){
		transformerFactory = TransformerFactory.newInstance();
	}

	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		final WebServiceMessage requestMessage = messageContext.getRequest();
		if(requestMessage instanceof SoapMessage){
			final SoapMessage soapMessage = (SoapMessage) requestMessage;
			// FIXME : Use better API instead of DOM 
			final String requestStr = getMessageString(new DOMSource(soapMessage.getDocument()));
			if(logRequest){
				Map<String,Object> reqLogKVPair = new LinkedHashMap<>();
				// TODO : Add other details along with request body here
				reqLogKVPair.put("Http Outbound Request Payload", requestStr);
				logPublisher.publishLog(new HttpCallLogEvent(reqLogKVPair));
			}
		}
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		final WebServiceMessage responseMessage = messageContext.getResponse();
		if(responseMessage instanceof SoapMessage){
			final SoapMessage soapMessage = (SoapMessage) responseMessage;
			// FIXME : Use better API instead of DOM 
			final String responseStr = getMessageString(new DOMSource(soapMessage.getDocument()));
			if(logResponse){
				Map<String,Object> resLogKVPair = new LinkedHashMap<>();
				// TODO : Add other details along with request body here
				resLogKVPair.put("Http Inbound Response Body", responseStr);
				logPublisher.publishLog(new HttpReturnLogEvent(resLogKVPair));
			}
		}
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		String faultBody = getMessageString(messageContext.getResponse().getPayloadSource());
		if(logFault){
			Map<String,Object> resLogKVPair = new LinkedHashMap<>();
			// TODO : Add other details along with request body here
			resLogKVPair.put("Http Inbound SoapFault Body", faultBody);
			logPublisher.publishLog(new HttpReturnLogEvent(resLogKVPair));
		}
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
		
	}
	
	private String getMessageString(Source source){
		final StringWriter stringWriter = new StringWriter();
		final StreamResult streamResult = new StreamResult(stringWriter);
		final Transformer transformer = createNonIndentingTransformer();
		transformer.transform(source, streamResult);
		stringWriter.flush();
		return stringWriter.toString();
	}
	
	private Transformer createNonIndentingTransformer() {
		final Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
		transformer.setOutputProperty(OutputKeys.INDENT,"no");
		return transformer;
	}

}
