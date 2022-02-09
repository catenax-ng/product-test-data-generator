package com.catenax.tdm.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {
	
	private static final String BASE_PACKAGE = "com.catenax.tdm.api";
	private static final String TITLE = "Catena-X Speedboat Test Data Generator (TDG)";
	private static final String LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";
	private static final String LICENSE = "Apache 2.0";
	private static final String CONTACT = "christian.kabelin@partner.bmw.de";
	private static final String DISCLAIMER = "Disclaimer: This service serves synthetic, none-productive data for testing purposes only. All BOMs, part trees, VINs, serialNos etc. are synthetic";
	private static final String VERSION = "1.0.1-SNAPSHOT";
	private static final String HEADER_API_KEY = "x-api-key";

	@Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                    .build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo())
        		.securitySchemes(Collections.singletonList(apiKey()))
        		.securityContexts(Collections.singletonList(securityContext()))
                ;
    }
	
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(TITLE).description(
				DISCLAIMER)
				.license(LICENSE).licenseUrl(LICENSE_URL)
				.termsOfServiceUrl("").version(VERSION)
				.contact(new Contact("", "", CONTACT)).build();
	}

	@Bean
	public OpenAPI openApi() {
		OpenAPI api = new OpenAPI();

		return api.info(new Info().title(TITLE).description(
				DISCLAIMER)
				.termsOfService("").version(VERSION)
				.license(new License().name(LICENSE).url(LICENSE_URL))
				.contact(new io.swagger.v3.oas.models.info.Contact().email(CONTACT)));
	}
	
	 private ApiKey apiKey() {
		 return new ApiKey(HEADER_API_KEY, HEADER_API_KEY, "header");
	 }
	 
	 private SecurityContext securityContext() {
	     return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
	 }
	 
	 private List<SecurityReference> defaultAuth() {
	    final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
	    return Collections.singletonList(new SecurityReference(HEADER_API_KEY, authorizationScopes));
	 }

}
