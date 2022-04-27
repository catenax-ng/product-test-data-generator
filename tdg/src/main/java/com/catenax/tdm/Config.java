package com.catenax.tdm;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class Config {

	public static String BASE_URL = "http://catena-x.net/tdg";
	public static Boolean VALIDATE = true;
	public static Boolean VALIDATE_EXIT = false;

}
