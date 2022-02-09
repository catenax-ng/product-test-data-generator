package com.catenax.tdm.ui;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.security.SecurityUtils;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("tdg-admin")
@PageTitle("Test Data Admin")
//@PWA(name = "Test Data Admin", shortName = "Test Data Admin")
public class TdgAdminView extends VerticalLayout {

	private static final Logger log = LoggerFactory.getLogger(TdgAdminView.class);

	public TdgAdminView() {

	}

	@PostConstruct
	private void init() {
		try {
			AppLayoutBasic app = new AppLayoutBasic();
			add(app);

			if (SecurityUtils.isUserLoggedIn()) {
				log.info("Username: " + SecurityUtils.getUsername());
			} else {
				log.info("No User logged in");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
