package com.catenax.tdm.security;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;

public class SecurityUtils {

	public static boolean isUserLoggedIn() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // (1)
	    return authentication != null // (2)
	            && !(authentication instanceof AnonymousAuthenticationToken) // (3)
	            && authentication.isAuthenticated(); // (4)
	}
	
	public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
		final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return parameterValue != null
				&& Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
	}

	public static String getUsername() {	    
	   	String result = null;
	   	
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(authentication != null) {
	    	result = authentication.getName();

	    	KeycloakPrincipal principal = 
	                (KeycloakPrincipal) SecurityContextHolder.getContext()
	                    .getAuthentication().getPrincipal();

	           KeycloakSecurityContext keycloakSecurityContext = 
	                principal.getKeycloakSecurityContext();
	           
	           result = keycloakSecurityContext.getIdToken().getPreferredUsername();
	    }
	    
	   	return result;
	}
	
}
