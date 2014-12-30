package com.capgemini.user.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.capgemini.user.service.util.FirstFailExceptionThreadLocalHolder;
import com.capgemini.user.service.util.UserExceptionThreadLocalHolder;

public class ClearThreadLocalFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// empty as of now, can be configured to process what specific threadlocal filter need to clear
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		try{
			// invoke the service
			chain.doFilter(request, response);
		}finally{
			FirstFailExceptionThreadLocalHolder.removeThrowable();
			UserExceptionThreadLocalHolder.removeUserException();
		}
	}

	@Override
	public void destroy() {
		// empty
	}

}
