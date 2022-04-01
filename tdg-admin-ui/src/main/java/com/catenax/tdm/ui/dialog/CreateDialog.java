package com.catenax.tdm.ui.dialog;

import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.client.model.TestDataScenario.ScriptTypeEnum;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public abstract class CreateDialog<T> extends Dialog {

	private static final long serialVersionUID = -6674735621758050699L;
	
	private Label title;
	private Label question;
	private Button confirm;
	
	private TestDataScenario result = new TestDataScenario();
	
	protected TextField fName = new TextField("name");
	protected TextField fVersion = new TextField("version");
	
	protected ComboBox<ScriptTypeEnum> fScriptTypeCombo = new ComboBox<TestDataScenario.ScriptTypeEnum>("Script Type");
	// protected ScriptTypeEnum fScriptType = TestDataScenario.ScriptTypeEnum.DSL;
	
	public CreateDialog() {
		createHeader();
		createContent();
		createFooter();
	}
	
	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setQuestion(String question) {
		this.question.setText(question);
	}
	
	public void addConfirmationListener(ComponentEventListener<ClickEvent<Button>> listener) {
		confirm.addClickListener(listener);
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
		question = new Label("Create new entry");

		VerticalLayout content = new VerticalLayout();
		content.add(question);
		content.setPadding(false);

		fVersion.setValue("1.0");

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
	
	protected void handleConfirm() {
		if(fName.getValue().trim().length() == 0) {
			return;
		}
		
		if(fVersion.getValue().trim().length() == 0) {
			return;
		}
		
		this.close();
	}
	
	public abstract T getData();

}
