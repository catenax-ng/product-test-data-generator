package com.catenax.tdm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerUiConfiguration implements WebMvcConfigurer {
	
	protected static final String PATH = "/swagger-ui";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		ResourceHandlerRegistration reg = registry.addResourceHandler(PATH + "/**");
		
		reg.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
		   .resourceChain(false);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		ViewControllerRegistration viewController = registry.addViewController(PATH + "/");
		
		viewController.setViewName("forward:" + PATH + "/index.html");
	}
}
