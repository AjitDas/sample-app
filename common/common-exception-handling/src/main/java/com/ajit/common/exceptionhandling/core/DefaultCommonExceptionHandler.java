package com.ajit.common.exceptionhandling.core;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ajit.common.exceptionhandling.exception.CommonException;

@Component("defaultCommonExceptionHandler")
public class DefaultCommonExceptionHandler implements CommonExceptionHandler{
	
	@Value("${error.mapping.properties.file:default-error-mapping.properties}")
	private String errorMappingPropFile;
	
	private Properties errorMappingProps = new Properties();
	
	@PostConstruct
	public void initialize(){
		if (this.errorMappingPropFile != null) {
			InputStream stream=null;
			try{
				stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(errorMappingPropFile);
				if(stream != null){
					errorMappingProps.load(stream);
				}
			}finally{
				if(stream!=null){
					stream.close();
				}
			}
		}
	}

	@Override
	public CommonException prepareCommonException(JoinPoint joinPoint, Throwable throwable) {
		CommonException customException = new CommonException(throwable.getMessage(),throwable);
		String exceptionClassName = throwable.getClass().getName();
		if(errorMappingProps.containsKey(exceptionClassName)){
			String errorMappingVal = errorMappingProps.getProperty(exceptionClassName);
			String[] splittedTokens = errorMappingVal.split("\\|");
			if(splittedTokens!=null && splittedTokens.length >= 2){
				customException.setErrorCode(splittedTokens[0]);
				customException.setErrorMessage(splittedTokens[1]);
			}else if(splittedTokens!=null && splittedTokens.length == 1){
				customException.setErrorCode(splittedTokens[0]);
				customException.setErrorMessage(throwable.getMessage());
			}else if(splittedTokens==null || splittedTokens.length == 0){
				customException.setErrorCode("XXXX");
				customException.setErrorMessage(throwable.getMessage());
			}
		}else{
			// set default code and error message
			customException.setErrorCode("XXXX");
			customException.setErrorMessage(throwable.getMessage());
		}
		return customException;
	}

}
