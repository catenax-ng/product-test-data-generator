package com.catenax.tdm.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class Config {

	public static String LOGIN_URL = "/login";
	public static String LOGIN_PROCESSING_URL = "/processing";
	public static String LOGIN_FAILURE_URL = "/failure";
	public static String LOGOUT_SUCCESS_URL = "/logout";



}
