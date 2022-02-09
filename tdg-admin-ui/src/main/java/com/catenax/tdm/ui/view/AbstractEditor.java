package com.catenax.tdm.ui.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.json.JSONObject;

import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.client.model.TestDataScenario.ScriptStatusEnum;
import com.catenax.tdm.client.model.TestDataScenario.ScriptTypeEnum;
import com.catenax.tdm.rest.TDGClient;
import com.catenax.tdm.ui.dialog.CreateDialog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import io.github.ciesielskis.AceEditor;
import io.github.ciesielskis.AceMode;
import io.github.ciesielskis.AceTheme;

public abstract class AbstractEditor<T> extends Div {

	private static final String LABEL_DOWNLOAD = "Download JSON";

	private static final long serialVersionUID = 1608298132734019198L;
	
	private TDGClient client = null;
	protected AceEditor aceEditor = null;
	protected AceEditor aceResult = null;
	protected HorizontalLayout toolbar = new HorizontalLayout();
	protected VerticalLayout dlContainer = new VerticalLayout();
	protected T selected = null;
	
	protected ListBox<T> itemList = new ListBox<>();
	
	private HorizontalLayout leftToolbar = null;
	private HorizontalLayout rightToolbar = null;
	
	protected abstract void selectItem(T scenario);
	
	protected abstract void loadData();
	
	protected abstract  void createNew(T result);
	
	protected abstract  void deleteSelection();
	
	protected abstract void saveCurrent();
	
	private CreateDialog<T> dialog = null;
	
	public AbstractEditor(CreateDialog<T> dialog) {
		this.dialog = dialog;
		init();
	}
	
	protected TDGClient getClient() {
		return this.client;
	}
	
	protected HorizontalLayout getLeftToolbar() {
		return this.leftToolbar;
	}
	
	protected HorizontalLayout getRightToolbar() {
		return this.rightToolbar;
	}
	
	protected void init() {
		client = new TDGClient();
		
		VerticalLayout vl = new VerticalLayout();
		
		int w = 1000;
		int h = 500;
		
		Button btnSave = new Button(VaadinIcon.DISC.create());
		Button btnNew = new Button(VaadinIcon.PLUS.create());
		Button btnDelete = new Button(VaadinIcon.MINUS.create());
		Button btnRefresh = new Button(VaadinIcon.REFRESH.create());
		
		btnSave.setText("Save");
		btnNew.setText("New");
		btnDelete.setText("Delete");
		
		
		// toolbar.add(btnSave, btnRun, btnNew, btnDelete);
		
		HorizontalLayout l = new HorizontalLayout();
		//l.setWidth((w + 50) + "px");
		//l.setHeight((h + 10) + "px");

		itemList.setHeight(h + "px");
		itemList.setWidth((w / 10 * 2) + "px");

		aceEditor = new AceEditor();
		aceEditor.setValue("");

		aceEditor.setTheme(AceTheme.github); //eclipse);
		// aceEditor.setMode(AceMode.javascript);
		aceEditor.setMode(AceMode.text);

		aceEditor.setFontSize(14);
		aceEditor.setSoftTabs(false);
		aceEditor.setTabSize(4);
		aceEditor.setWidth((w / 10 * 8) + "px");
		aceEditor.setHeight(h + "px");
		
		aceResult = new AceEditor();
		aceResult.setWidth(w + "px");
		aceResult.setHeight("500px");
		aceResult.setReadOnly(true);
		aceResult.setVisible(false);
		
		vl.add(toolbar);
		vl.add(l);
		vl.add(aceResult);
		vl.add(dlContainer);
		
		this.leftToolbar = new HorizontalLayout(btnNew, btnDelete, btnRefresh);
		this.rightToolbar = new HorizontalLayout(btnSave);
		
		l.add(new VerticalLayout(leftToolbar, itemList));
		l.add(new VerticalLayout(rightToolbar, aceEditor));
		
		this.add(vl);
		
		itemList.addValueChangeListener(new ValueChangeListener<ValueChangeEvent<T>>() {

			@Override
			public void valueChanged(ValueChangeEvent<T> event) {
				if(event != null && event.getValue() != null) {
					selectItem(event.getValue());
				} else {
					// aceEditor.setValue("");
				}
			}
			
		});
		
		btnNew.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				dialog.addConfirmationListener(new ComponentEventListener<ClickEvent<Button>>() {
					@Override
					public void onComponentEvent(ClickEvent<Button> event) {
						createNew(dialog.getData());
					}
				});
				dialog.open();
			}
		});
		
		btnDelete.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				deleteSelection();
			}
		});
		
		btnSave.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				saveCurrent();
			}
		});
		
		btnRefresh.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				loadData();
			}
		});
		
		loadData();
	}
	
	protected void addClickListener() {
		
	}
	
	protected String _fixContent(String content) {
		String result = content
				//.replaceAll("(\\r|\\n|\\r\\n)", "\n")
				.replaceAll("\\t", "    ")
				.replaceAll("\\\\n", "\n")
				.replaceAll("\\\\r", "")
				.replaceAll("\\\\u0027", "'")
				.replaceAll("\\\\u003d", "=")
				.replaceAll("\\\\\"", "\"")
		;
		
		if(result.startsWith("\"")) {
			result = result.substring(1);
		}
		
		if(result.endsWith("\"")) {
			result = result.substring(0, result.length() - 1);
		}
		
		return result;
	}
	
	protected String prettyPrintJs(String js) {
		String result = js;
		JSONObject jo = new JSONObject(js);
		ObjectMapper om = new ObjectMapper();
		try {
			result = om.writerWithDefaultPrettyPrinter().writeValueAsString(om.readTree(js));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected void clearDownloadableContent() {
		dlContainer.removeAll();
	}
	
	protected void setDownloadableContent(String js) {
		try {
			dlContainer.removeAll();
			
			InputStreamFactory isf = new InputStreamFactory() {
				@Override
				public InputStream createInputStream() {
					return new ByteArrayInputStream(js.getBytes());
				}
			};
			
			StreamRegistration streamRegistration = VaadinSession.getCurrent().getResourceRegistry().registerResource(
					
					new StreamResource(
							"result.json", 
							isf
					)
					
			);
			
			Anchor anchor = new Anchor(streamRegistration.getResource(), LABEL_DOWNLOAD);
			
			dlContainer.add(anchor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
