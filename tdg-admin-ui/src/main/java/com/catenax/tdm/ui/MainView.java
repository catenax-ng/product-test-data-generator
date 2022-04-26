package com.catenax.tdm.ui;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.Env;
import com.catenax.tdm.security.SecurityUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route
@PageTitle("Test Data Admin")
// @PWA(name = "Test Data Admin", shortName = "Test Data Admin")
public class MainView extends VerticalLayout {

	private static final Logger log = LoggerFactory.getLogger(MainView.class);

	public MainView() {

	}

	@PostConstruct
	private void init() {
		try {
			// AppLayoutBasic app = new AppLayoutBasic();
			// add(app);

			// Anchor a = new Anchor("/tdg-admin", "TDG Admin (authentication required)");
			// add(a);

			Label lbl = new Label(
					"Please use the following url to authenticate and bookmark: " + Env.getBaseUrl() + "/tdg-admin");
			add(lbl);

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
