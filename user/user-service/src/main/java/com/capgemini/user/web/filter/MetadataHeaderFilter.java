package com.capgemini.user.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.user.logging.core.AppContextSingleton;
import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.SimpleLogEvent;
import com.capgemini.user.service.util.MetadataHeaderThreadLocalHolder;
import com.capgemini.user.service.util.MetadataHeaderThreadLocalHolder.MetadataHeaders;
import com.capgemini.user.web.filter.http.HttpRequestWrapper;
import com.capgemini.user.web.filter.http.HttpResponseWrapper;

public class MetadataHeaderFilter implements Filter {

	private static final LogPublisher logPublisher = AppContextSingleton.getInstance().getLogPublisher();
	
	private Set<MetadataHeaders> configMetadataHeaders = new HashSet<>();
	private Set<MetadataHeaders> nonEmptyMetadataHeaders = new HashSet<>();


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		List<String> metadataHeaderNames = Arrays.asList(filterConfig.getInitParameter("metadataHeaderNames").split(";"));
		for(MetadataHeaders metadataHeader: MetadataHeaders.values()){
			if(metadataHeaderNames.contains(metadataHeader.getMetadataHeaderName())){
				configMetadataHeaders.add(metadataHeader);
			}
		}
		
		List<String> nonEmptyMetadataHeaderNames = Arrays.asList(filterConfig.getInitParameter("nonEmptyMetadataHeaderNames").split(";"));
		for(MetadataHeaders metadataHeader: MetadataHeaders.values()){
			if(nonEmptyMetadataHeaderNames.contains(metadataHeader.getMetadataHeaderName())){
				nonEmptyMetadataHeaders.add(metadataHeader);
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse) ){
			throw new IllegalStateException("'MetadataHeaderFilter' works with only HTTP protocol");
		}	
		try {
			HttpRequestWrapper requestWrapper = new HttpRequestWrapper((HttpServletRequest)request);
			HttpResponseWrapper responseWrapper = new HttpResponseWrapper((HttpServletResponse)response);
			// copy all metadata headers from request header to threadlocal
			for(MetadataHeaders metadataHeader: configMetadataHeaders){
				String headerValue = getHeaderFromRequest(requestWrapper,metadataHeader.getMetadataHeaderName());
				
				logPublisher.publishLog(new SimpleLogEvent(String.format("Metadata Header %s retrieved from HTTP Request %s",metadataHeader.getMetadataHeaderName(),headerValue),"DEBUG"));
				// generate UUID if it's a NON empty header and the header value is empty
				if((headerValue==null || headerValue.isEmpty() || headerValue.equals("null")) && nonEmptyMetadataHeaders.contains(metadataHeader)){
					headerValue = UUID.randomUUID().toString();
				}
				if(headerValue!=null && !headerValue.isEmpty()){
					MetadataHeaderThreadLocalHolder.putMetadatHeaderToThreadLocal(metadataHeader, headerValue);
				}
			}

			// copy all metadata headers from threadlocal back to response header
			for(MetadataHeaders metadataHeader: configMetadataHeaders){
				String threadLocalValue = MetadataHeaderThreadLocalHolder.getMetadatHeaderFromThreadLocal(metadataHeader);
				logPublisher.publishLog(new SimpleLogEvent(String.format("Metadata Header %s retrieved from ThreadLocal %s",metadataHeader.getMetadataHeaderName(),threadLocalValue),"DEBUG"));
				if(threadLocalValue!=null && !threadLocalValue.isEmpty()){
					addHeaderToResponse((HttpServletResponse)responseWrapper,metadataHeader.getMetadataHeaderName(), threadLocalValue);
				}
			}

			// invoke the service
			chain.doFilter(requestWrapper, responseWrapper);

		}finally{
			// remove all the theadlocal headers so that it's safe to reuse the thread in a managed thread pool provided by servlet container
			MetadataHeaderThreadLocalHolder.cllearMetadataHeaderFromThreadLocal();
		}

	}

	@Override
	public void destroy() {

	}

	public String getHeaderFromRequest(HttpServletRequest request, String requestHeaderName){
		if(requestHeaderName!=null && !requestHeaderName.isEmpty()){
			return request.getHeader(requestHeaderName);
		}
		return null;
	}

	public void addHeaderToResponse(HttpServletResponse response, String responseHeaderName, String responseHeaderValue){
		if(responseHeaderName!=null && !responseHeaderName.isEmpty()){
			response.setHeader(responseHeaderName, responseHeaderValue);
		}
	}
}
