package com.catenax.tdm.ui.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class YesNoDialog extends Dialog {
	
	private Label question;
	private Runnable confirm = null;
	
	public YesNoDialog(String questionStr, Runnable confirm) {
		this.question = new Label(questionStr);
		this.confirm = confirm;
		init();
	}
	
	private void init() {
		VerticalLayout content = new VerticalLayout();
		content.add(question);
		content.setPadding(false);

		Button btnYes = new Button("Yes");
		Button btnNo = new Button("No");
		
		content.add(new HorizontalLayout(btnNo, btnYes));
		
		btnYes.addClickListener(ClickEvent -> clickYes());
		btnNo.addClickListener(ClickEvent -> clickNo());

		add(content);
	}
	
	private void clickNo() {
		
		this.close();
	}
	
	private void clickYes() {
		if(this.confirm != null) {
			this.confirm.run();
		}
		this.close();
	}

}
