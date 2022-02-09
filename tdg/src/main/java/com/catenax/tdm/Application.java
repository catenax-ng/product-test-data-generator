package com.catenax.tdm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class
})
@EnableOpenApi
@ComponentScan(basePackages = { 
		"com.catenax.tdm", 
		"com.catenax.tdm.dao", 
		"com.catenax.tdm.api",
		"com.catenax.tdm.configuration" 
})
@EntityScan(basePackages = { 
		"com.catenax.tdm.model" 
})
// @EnableJpaRepositories(basePackages = { "com.catenax.tdm.dao" })
// @Theme(themeClass = Lumo.class, variant = "dark")
public class Application implements CommandLineRunner /*, AppShellConfigurator */ {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	static class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final int DEFAULT_EXIT_CODE = 10;
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return DEFAULT_EXIT_CODE;
		}

	}

	public static void main(String[] args) throws Exception {
		// new SpringApplication(Application.class).run(args);
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
	}
	/*
	@Configuration
    static class CustomDateConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
            registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        }
    }
	*/
	/*
	@Bean
	public ServletRegistrationBean<SpringServlet> springServlet(ApplicationContext context) {
		ServletRegistrationBean<SpringServlet> result = null;
		
	    try {
	    	SpringServlet servlet = new SpringServlet(context, false);
			result = new ServletRegistrationBean<SpringServlet>(servlet, "/ui/*", "/frontend/*");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	    
	    return result;
	}
	*/
	
	public Application() {
		log.info("Instance of Application created upon boot...");
	}
}
