package com.capgemini.user.exception.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("defaultExceptionHttpStatusMapper")
public class DefaultExceptionHttpStatusMapper implements ExceptionHttpStatusMapper {
	
	private Map<String, Integer> exceptionHttpStatusMap = new HashMap<>();
	private Properties errorHttpStatusMappingProps= new Properties();
	
	@Value("${app.name:user-app}")
	private String appname;

	@Value("${error.httpstatus.mapping.properties.file:default-error-httpstatus-mapping.properties}")
	private String errorHttpStatusMappingPropFile;
	
	@PostConstruct
	public void initialize(){
		if (this.errorHttpStatusMappingPropFile != null) {
			InputStream stream=null;
			try {
				stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(errorHttpStatusMappingPropFile);
				if(stream == null){
					stream = DefaultExceptionHttpStatusMapper.class.getClassLoader().getResourceAsStream(errorHttpStatusMappingPropFile);
				}
				if (stream != null) {
					errorHttpStatusMappingProps.load(stream);
				}
			} finally{
				if(stream!=null){
					stream.close();
				}
			}			
		}
	}
	
	@Override
	public Map<String, Integer> populateExceptionHttpStatusMap() {
		for(Map.Entry<Object, Object> keySet: errorHttpStatusMappingProps.entrySet()){
			String exceptionClassName = String.valueOf(keySet.getKey());
			Integer httpStatusCode = Integer.valueOf(String.valueOf(keySet.getValue()));
			exceptionHttpStatusMap.put(exceptionClassName, httpStatusCode);
		}
		return exceptionHttpStatusMap;
	}

}
