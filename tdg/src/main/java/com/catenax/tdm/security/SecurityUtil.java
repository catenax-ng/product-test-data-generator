package com.catenax.tdm.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtil {

private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);
	
	private SecurityUtil() {
        // Util methods only
    }
	/*
	public static boolean isFrameworkInternalRequest(HttpServletRequest request) { 
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
            && Stream.of(RequestType.values())
            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }
	*/
	/*
	public static boolean isFrameworkInternalRequest(RequestMatcherConfigurer requestMatcher) { 
		boolean result = true;
		
		return result;
    }
	*/
	
	/*
	public static boolean isUserLoggedIn() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication != null
	            && !(authentication instanceof AnonymousAuthenticationToken)
	            && authentication.isAuthenticated();
	}
	
	public static String getUsername() {	    
	    String result = null;
	    try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			result = authentication.getName();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	    return result;
	}
	
	public static Authentication getUser() {	    
	    return SecurityContextHolder.getContext().getAuthentication();
	}
	*/
	
}
