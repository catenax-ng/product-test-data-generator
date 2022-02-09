package com.catenax.tdm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.catenax.tdm.configuration.LocalDateConverter;
import com.catenax.tdm.configuration.LocalDateTimeConverter;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan(basePackages = { "com.catenax.tdm", "com.catenax.tdm.api", "com.catenax.tdm.configuration", "com.catenax.tdm.ui", "com.catenax.tdm.dao", "com.catenax.tdm.security"})
@EntityScan(basePackages = {"com.catenax.tdm.model"})
@Theme(themeClass = Lumo.class, variant = "dark")
public class Application implements CommandLineRunner, AppShellConfigurator  {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }
	
	@Configuration
    static class CustomDateConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
            registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        }
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

}
