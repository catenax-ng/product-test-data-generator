package com.catenax.tdm.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public String index() {
		// System.out.println("/swagger-ui/index.html");
		String result = "redirect:/api/swagger-ui/";
		return result;
	}

}
