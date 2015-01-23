package com.capgemini.user.web.converter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.capgemini.user.logging.core.AppContextSingleton;
import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.LogEventTypes;
import com.capgemini.user.logging.event.SimpleLogEvent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomDateDeserializer extends StdDeserializer<Date> {
	
	/** default serial version uid */
	private static final long serialVersionUID = 1L;
	private static final LogPublisher logPublisher = AppContextSingleton.getInstance().getLogPublisher();
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	protected CustomDateDeserializer() {
		super(Date.class);
	}

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			// do custom processing for specific class and fields
			String fieldName = jp.getParsingContext().getCurrentName();
			
			logPublisher.publishLog(new SimpleLogEvent(String.format("Using CustomDateDeSerializer for attribute name=%s",fieldName), LogEventTypes.DEBUG.toString()));
			
            return dateFormat.parse(jp.getText());
        } catch (final ParseException e) {
            return null;
        }
	}


}
