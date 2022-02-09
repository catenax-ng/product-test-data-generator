package com.catenax.tdm.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("error")
@PageTitle("Error")
public class ErrorPage extends VerticalLayout {

	public ErrorPage() {
		init();
	}
	
	private void init() {
		add(new Label("Uhh ohhh, something went not so well ..."));
	}
	
}
