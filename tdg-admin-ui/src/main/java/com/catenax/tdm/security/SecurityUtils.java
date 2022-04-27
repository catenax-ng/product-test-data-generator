package com.catenax.tdm.security;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.catenax.tdm.Env;
import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;

public class SecurityUtils {

	private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);

	public static boolean isUserLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // (1)
		return authentication != null
				&& !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated() && getUsername() != null;
	}

	public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
		final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return parameterValue != null
				&& Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
	}

	public static String getUsername() {
		String result = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			// result = authentication.getName();

			try {
				Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				if (o instanceof KeycloakPrincipal) {
					KeycloakPrincipal principal = (KeycloakPrincipal) o;

					KeycloakSecurityContext keycloakSecurityContext = principal.getKeycloakSecurityContext();

					result = keycloakSecurityContext.getIdToken().getPreferredUsername();
					log.info(("PRINCIPAL USER: " + result));
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		if (result != null && result.equals("anonymousUser")) {
			result = null;
		}

		return result;
	}

	public static String getLogoutUrl() {
		return Env.get(Env.TDG_IAM_SERVER_URL, null) + "/realms/" + Env.get(Env.TDG_IAM_REALM, null) +
				"/protocol/openid-connect/logout?redirect_uri=" +
				Env.getBaseUrl() + "/tdg-admin";
	}

}
