package com.capgemini.user.web.converter;

import java.util.Date;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomJsonMapper extends ObjectMapper{

	/** default serial version uid */
	private static final long serialVersionUID = 1L;

	public CustomJsonMapper() {
        SimpleModule module = new SimpleModule("JSONModule", new Version(2, 0, 0, null, null, null));
        module.addSerializer(Date.class, new CustomDateSerializer());
        module.addDeserializer(Date.class, new CustomDateDeserializer());
        registerModule(module);
    }
}
