package com.capgemini.user.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.SoftException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.HttpCallLogEvent;
import com.capgemini.user.logging.event.HttpReturnLogEvent;
import com.capgemini.user.logging.event.SimpleLogEvent;
import com.capgemini.user.web.filter.http.HttpRequestWrapper;
import com.capgemini.user.web.filter.http.HttpResponseWrapper;

@Component("boundaryLoggingFilter")
public class BoundaryLoggingFilter implements Filter {

	private boolean boundaryTraceEnabled;
	
	@Autowired @Qualifier("logPublisher") 
	private LogPublisher logPublisher;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String boundaryTraceString = filterConfig.getInitParameter("enableBoundayTrace");
		if(boundaryTraceString!=null && !boundaryTraceString.isEmpty()){
			boundaryTraceEnabled = Boolean.valueOf(boundaryTraceString);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse) ){
			throw new IllegalStateException("'BoundaryLoggingFilter' works with only HTTP protocol");
		}
		// get all the meta-data headers and put in thread local
		request = new HttpRequestWrapper((HttpServletRequest)request);
		response = new HttpResponseWrapper((HttpServletResponse)response);
		try {
			chain.doFilter(request, response);
			//response.flushBuffer();
		}finally {
			if(boundaryTraceEnabled){
				// get the meta-data headers and log along with request/response
				logRequestResponse((HttpServletRequest)request,(HttpResponseWrapper)response);
			}
		}
	}


	@Override
	public void destroy() {

	}
	
	private void logRequestResponse(HttpServletRequest request, HttpResponseWrapper response) {
		
		// HTTP Request
		
		Map<String,Object> reqLogKVPair = new LinkedHashMap<>();
		HttpSession session = request.getSession(false);
		if (session != null) {
			reqLogKVPair.put("Session Id", session.getId());
		}
		reqLogKVPair.put("Http Method", request.getMethod());
		StringBuilder reqURI = new StringBuilder();
		reqURI.append(request.getRequestURI());
		if(request.getQueryString() != null) {
			reqURI.append('?').append(request.getRequestURI());
		}
		reqLogKVPair.put("URI", reqURI.toString());
		Enumeration<?> headerNames= request.getHeaderNames();
		Map<String,String> httpReqHeaders = new HashMap<>();
		while(headerNames.hasMoreElements()){
			String headerName = String.valueOf(headerNames.nextElement());
			String headerValue = request.getHeader(headerName);
			httpReqHeaders.put(headerName, headerValue);
		}
		reqLogKVPair.put("Http Request Headers", httpReqHeaders);
		
		if(request instanceof HttpRequestWrapper && !isMultipart(request)){
			HttpRequestWrapper requestWrapper = (HttpRequestWrapper) request;
			try {
				String charEncoding = requestWrapper.getCharacterEncoding() != null ? requestWrapper.getCharacterEncoding() :"UTF-8";
				String payload = new String(requestWrapper.toByteArray(), charEncoding);
				reqLogKVPair.put("Http Request Payload", payload);
			} catch (SoftException e) {
				// This catch is doing nothing other than logging, should have been handled by AOP shown here just for demonstration purpose
				logPublisher.publishLog(new SimpleLogEvent("Failed to parse request payload" +e.getMessage(), "WARN"));
			}
		}
		
		logPublisher.publishLog(new HttpCallLogEvent(reqLogKVPair));
		
		// HTTP Response
		Map<String,Object> resLogKVPair = new LinkedHashMap<>();
		if (session != null) {
			resLogKVPair.put("Session Id", session.getId());
		}
		resLogKVPair.put("Http Method", request.getMethod());
		resLogKVPair.put("Request URI", reqURI.toString());
		String resContentType = response.getContentType();
		if(resContentType!=null){
			resLogKVPair.put("Content-Type", resContentType);
		}
		if(response instanceof HttpResponseWrapper){
			resLogKVPair.put("Http Status", ((HttpResponseWrapper)response).getStatus());
			String responsePayload = null;
			try {
				responsePayload = new String(response.toByteArray(), response.getCharacterEncoding());
			} catch (SoftException e) {
				// This catch is doing nothing other than logging, should have been handled by AOP shown here just for demonstration purpose
				logPublisher.publishLog(new SimpleLogEvent("Failed to parse response payload" +e.getMessage(), "WARN"));
			}
			if(responsePayload!=null){
				resLogKVPair.put("Http Response Payload", responsePayload);
			}
		}
		logPublisher.publishLog(new HttpReturnLogEvent(resLogKVPair));
	}


	private boolean isMultipart(final HttpServletRequest request) {
		return request.getContentType()!=null && request.getContentType().startsWith("multipart/form-data");
	}
	
}
