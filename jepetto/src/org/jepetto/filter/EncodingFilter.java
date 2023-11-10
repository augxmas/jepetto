package org.jepetto.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ServletRequest객체에 인코딩을 설정하는 Filter클래스.
 * 
 * @web.filter name="Encoding Filter" 
 * 			   display-name="Encoding Filter"
 * 	
 * @web.filter-init-param name="encoding"
 * 						  value="euc-kr"		    
 * 
 * @web.filter-mapping url-pattern="/*"
 * 
 * 					   
 */


public class EncodingFilter implements Filter {

	private String encoding = null;

	protected FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)	throws IOException, ServletException {
		
		chain.doFilter(request, response);
	}
	
	
	public void destroy() {

		this.encoding = null;
		this.filterConfig = null;

	}
	
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig cfg) {
		filterConfig = cfg;
	}
}
