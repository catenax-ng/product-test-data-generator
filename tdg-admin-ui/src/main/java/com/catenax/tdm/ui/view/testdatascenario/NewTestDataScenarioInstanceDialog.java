package com.catenax.tdm.ui.view.testdatascenario;

import com.catenax.tdm.client.ApiException;
import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.rest.TDGClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class NewTestDataScenarioInstanceDialog extends Dialog {

	private TestDataScenario scenario = null;
	private TDGClient client = null;
	
	private Label title;
	private Label question;
	private Button confirm;
	
	protected TextField fInstance = new TextField("instance");	
	protected TextField fName = new TextField("name");
	protected TextField fVersion = new TextField("version");
	
	public NewTestDataScenarioInstanceDialog(TDGClient client, TestDataScenario scenario) {
		this.client = client;
		this.scenario = scenario;
		
		createHeader();
		createContent();
		createFooter();
	}
	
	protected void createHeader() {
		this.title = new Label();
		Button close = new Button();
		close.setIcon(VaadinIcon.CLOSE.create());
		close.addClickListener(buttonClickEvent -> close());

		HorizontalLayout header = new HorizontalLayout();
		header.add(this.title, close);
		header.setFlexGrow(1, this.title);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		add(header);
	}

	protected void createContent() {
		question = new Label("Create new instance");

		VerticalLayout content = new VerticalLayout();
		content.add(question);
		content.setPadding(false);

		fName.setValue(this.scenario.getName());
		fVersion.setValue(this.scenario.getVersion());
		
		fName.setEnabled(false);
		fVersion.setEnabled(false);

		content.add(fInstance);
		content.add(fName);
		content.add(fVersion);

		add(content);
	}

	protected void createFooter() {
		Button abort = new Button("Cancel");
		abort.addClickListener(buttonClickEvent -> close());
		
		confirm = new Button("Create");
		confirm.addClickListener(buttonClickEvent -> handleConfirm());

		HorizontalLayout footer = new HorizontalLayout();
		footer.add(abort, confirm);
		footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

		add(footer);
	}
	
	private void handleConfirm() {
		if(fInstance.getValue().trim().length() == 0) {
			return;
		}
		
		if(fName.getValue().trim().length() == 0) {
			return;
		}
		
		if(fVersion.getValue().trim().length() == 0) {
			return;
		}
		
		try {
			this.client.getTestdataScenarioInstanceApi()
				.instantiateTestdataScenarioUsingGET(
						fName.getValue(), 
						fVersion.getValue(), 
						fInstance.getValue(), 
						true, false);
		} catch (ApiException e) {
			e.printStackTrace();
		}
			
		
		this.close();
	}
	
}
