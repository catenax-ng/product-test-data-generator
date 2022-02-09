package com.catenax.tdm.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(RestDelegate.class);

	@GetMapping("/helloWorld")
    public String getHelloWorld() {
		String result = "Hello World!";
		log.info("Hello World: " + result);
        return result;
    }
	
}
