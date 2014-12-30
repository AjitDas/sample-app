package com.capgemini.user.exception.core;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capgemini.user.exception.UserException;

@Component("defaultExceptionHandler")
public class DefaultExceptionHandler implements ExceptionHandler{
	
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
	public UserException prepareUserException(JoinPoint joinPoint, Throwable throwable) {
		UserException userException = new UserException(throwable.getMessage(),throwable);
		String exceptionClassName = throwable.getClass().getName();
		if(errorMappingProps.containsKey(exceptionClassName)){
			String errorMappingVal = errorMappingProps.getProperty(exceptionClassName);
			String[] splittedTokens = errorMappingVal.split("|");
			if(splittedTokens!=null && splittedTokens.length >= 2){
				userException.setErrorCode(splittedTokens[0]);
				userException.setErrorMessage(splittedTokens[1]);
			}else if(splittedTokens!=null && splittedTokens.length == 1){
				userException.setErrorCode(splittedTokens[0]);
				userException.setErrorMessage(throwable.getMessage());
			}else if(splittedTokens==null || splittedTokens.length == 0){
				userException.setErrorCode("XXXX");
				userException.setErrorMessage(throwable.getMessage());
			}
		}else{
			// set default code and error message
			userException.setErrorCode("XXXX");
			userException.setErrorMessage(throwable.getMessage());
		}
		return userException;
	}

}
