package com.catenax.tdm.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ApiKeyFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(ApiKeyFilter.class);
	private static final String API_KEY = System.getenv().get("TDG_API_KEY");
	
	public ApiKeyFilter() {

	}

    @Override
    public void doFilter(
      ServletRequest request, 
      ServletResponse response, 
      FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI().toLowerCase();

        if(url.startsWith("/catena")) { // whitelist.contains(req.getRequestURI())) {
        	String key = req.getHeader("x-api-key");

        	if(!matchApiKey(key)) {
        		log.error("Invalid API-KEY: " + key);
        		throw new RuntimeException("Invalid API-KEY");
        	}
        }
        
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");

        chain.doFilter(request, response);
    }
    
    private boolean matchApiKey(String key) {
    	return API_KEY.equals(key);
    }

}
