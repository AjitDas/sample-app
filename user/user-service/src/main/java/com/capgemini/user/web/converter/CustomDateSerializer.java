package com.capgemini.user.web.converter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.capgemini.user.logging.core.AppContextSingleton;
import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.LogEventTypes;
import com.capgemini.user.logging.event.SimpleLogEvent;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomDateSerializer extends StdSerializer<Date> {
	
	private static final LogPublisher logPublisher = AppContextSingleton.getInstance().getLogPublisher();
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public CustomDateSerializer() {
		super(Date.class);
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
		// do custom processing for specific class and fields
		String fieldName = jgen.getOutputContext().getCurrentName();
		
		logPublisher.publishLog(new SimpleLogEvent(String.format("Using CustomDateSerializer for attribute name=%s",fieldName), LogEventTypes.DEBUG.toString()));
		
		String out = dateFormat.format(value);
		jgen.writeString(out);
	}

}
