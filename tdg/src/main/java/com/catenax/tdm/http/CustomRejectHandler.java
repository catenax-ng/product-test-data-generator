package com.catenax.tdm.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRejectHandler /* extends HttpStatusRequestRejectedHandler */ {
	
	private static final Logger log = LoggerFactory.getLogger(CustomRejectHandler.class);
	
	public CustomRejectHandler() {
		super();
	}

	/*
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			RequestRejectedException requestRejectedException) throws IOException {
		log.error("Request Rejected by firewall: " + requestRejectedException.getMessage());
		log.error(" " + request.getRequestURL().toString());
		super.handle(request, response, requestRejectedException);
	}
	*/

}
