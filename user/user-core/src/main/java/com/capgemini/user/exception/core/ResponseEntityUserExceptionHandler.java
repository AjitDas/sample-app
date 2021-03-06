package com.capgemini.user.exception.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.capgemini.user.exception.UserException;
import com.capgemini.user.exception.core.EnableExceptionHandler;
import com.capgemini.user.exception.core.ErrorResponse;
import com.capgemini.user.exception.core.ExceptionHttpStatusMapper;

@ControllerAdvice(annotations=EnableExceptionHandler.class)
//@ControllerAdvice
public class ResponseEntityUserExceptionHandler implements ApplicationContextAware {
	
	private static final HttpStatus defaultHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	
	private ApplicationContext applicationConext;
	
	@Autowired @Qualifier("defaultExceptionHttpStatusMapper")
	private ExceptionHttpStatusMapper defaultExceptionHttpStatusMapper;
	
	//@Autowired(required=false) @Lazy @Qualifier("customExceptionHttpStatusMapper")
	private ExceptionHttpStatusMapper customExceptionHttpStatusMapper;
	
	@ExceptionHandler(value={UserException.class})
	public ResponseEntity<ErrorResponse> handleUserException(UserException userException, HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		HttpStatus httpStatus = defaultHttpStatus;
		Map<String, Integer> exceptionHttpStatusMap = null;
		try{
			if(applicationConext!=null && customExceptionHttpStatusMapper == null){
				try{
					customExceptionHttpStatusMapper = applicationConext.getBean("customExceptionHttpStatusMapper", ExceptionHttpStatusMapper.class);
				}catch(final Exception exception){
					// ignore in case the bean is not present in the context, fall back to default implementation
				}
			}
			if(customExceptionHttpStatusMapper!=null){
				exceptionHttpStatusMap = customExceptionHttpStatusMapper.populateExceptionHttpStatusMap();
			}
			if(exceptionHttpStatusMap == null && defaultExceptionHttpStatusMapper!=null){
				exceptionHttpStatusMap = defaultExceptionHttpStatusMapper.populateExceptionHttpStatusMap();
			}
			
			Throwable cause = userException;
			if(userException.getCause() !=null){
				cause = userException.getCause();
			}
			String exceptionClassName = cause.getClass().getName();
			if(exceptionHttpStatusMap.containsKey(exceptionClassName)){
				Integer httpStatusCode = exceptionHttpStatusMap.get(exceptionClassName);
				try{
					httpStatus = HttpStatus.valueOf(httpStatusCode);
				}catch(IllegalArgumentException ex){
					// in case status is either not present in HttpStatus or invalid status code passed
					httpStatus = defaultHttpStatus;
				}
			}
			
			errorResponse.setErrorCode(userException.getErrorCode());
			errorResponse.setErrorMessage(userException.getErrorMessage());
			StringBuilder reqURI = new StringBuilder();
			reqURI.append(request.getRequestURI());
			if(request.getQueryString() != null) {
				reqURI.append('?').append(request.getRequestURI());
			}
			errorResponse.setRequestedUrl(reqURI.toString());
			
		}finally{
			// empty
		}
		return new ResponseEntity<ErrorResponse>(errorResponse, httpStatus);
	}
	
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationConext = applicationContext;
	}
}
