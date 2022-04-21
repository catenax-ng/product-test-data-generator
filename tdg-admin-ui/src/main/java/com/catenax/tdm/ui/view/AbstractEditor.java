package com.catenax.tdm.ui.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.rest.TDGClient;
import com.catenax.tdm.ui.dialog.CreateDialog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import io.github.ciesielskis.AceEditor;
import io.github.ciesielskis.AceMode;
import io.github.ciesielskis.AceTheme;

public abstract class AbstractEditor<T> extends Div {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractEditor.class);

	private static final String LABEL_DOWNLOAD = "Download Testdata (JSON)";

	private static final long serialVersionUID = 1608298132734019198L;
	
	private TDGClient client = null;
	
	protected Div master = new Div();
	protected Div detail = new Div();
	protected Div detailMaster = new Div();

	protected SplitLayout splitLayout = new SplitLayout(master, detailMaster);
	
	protected AceEditor aceEditor = null;
	protected AceEditor aceResult = null;
	protected HorizontalLayout toolbar = new HorizontalLayout();
	protected Span dlContainer = new Span();
	protected T selected = null;
	
	protected List<T> items = new ArrayList<T>();
	protected ListBox<T> itemList = new ListBox<>();
	
	private HorizontalLayout leftToolbar = null;
	private HorizontalLayout rightToolbar = null;
	
	protected abstract void selectItem(T scenario);
	
	protected abstract void loadData();
	
	protected abstract  void createNew(T result);
	
	protected abstract  void deleteSelection();
	
	protected abstract void saveCurrent();
	
	protected Button btnNew = new Button(VaadinIcon.PLUS.create());
	
	private CreateDialog<T> dialog = null;
	
	private TextField searchBox = new TextField("");
	
	private Label editorTitle = new Label("Please select item");
	
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
		
		// this.setSizeFull();
		
		// this.splitLayout.setSizeFull();
		this.splitLayout.setSplitterPosition(25);
		
		Button btnSave = new Button(VaadinIcon.DISC.create());
		
		Button btnDelete = new Button(VaadinIcon.MINUS.create());
		Button btnRefresh = new Button(VaadinIcon.REFRESH.create());
		
		btnSave.setText("Save");
		btnNew.setText("New");
		btnDelete.setText("Delete");
		
		this.leftToolbar = new HorizontalLayout(btnNew, btnDelete, btnRefresh);
		this.rightToolbar = new HorizontalLayout(btnSave);
		
		Button clearSearch = new Button(VaadinIcon.DEL.create());
		clearSearch.setWidth("20px");
		HorizontalLayout search = new HorizontalLayout(searchBox, clearSearch);
		
		searchBox.setTitle("search");
		searchBox.setWidth("250px");
		
		editorTitle.setWidth("600px");
		
		dlContainer.setHeight(editorTitle.getHeight());
		
		itemList.setSizeFull();
		
		// Content Editor
		aceEditor = new AceEditor();
		aceEditor.setValue("");

		aceEditor.setTheme(AceTheme.github); //eclipse);
		// aceEditor.setMode(AceMode.javascript);
		aceEditor.setMode(AceMode.text);

		aceEditor.setFontSize(14);
		aceEditor.setSoftTabs(false);
		aceEditor.setTabSize(4);
		//aceEditor.setWidth("800px");
		//aceEditor.setHeight(h + "px");
		
		//aceEditor.setSizeFull();
		
		this.searchBox.setPrefixComponent(VaadinIcon.SEARCH.create());

		// element composition
		master.add(this.leftToolbar);
		master.add(this.searchBox);
		master.add(this.itemList);
		
		HorizontalLayout hlTop = new HorizontalLayout();
		hlTop.add(editorTitle);
		hlTop.add(dlContainer);
		
		detailMaster.add(this.rightToolbar);
		
		detailMaster.add(this.detail);	
		detail.add(hlTop);
		detail.add(this.aceEditor);
		
		
		add(this.splitLayout);
		
		// initialize actions
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
		
		this.dialog.addConfirmationListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				createNew(dialog.getData());
			}
		});
		
		btnNew.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
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
				dlContainer.removeAll();
			}
		});
		
		btnRefresh.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				loadData();
				dlContainer.removeAll();
			}
		});

		searchBox.addValueChangeListener(event -> {
			log.info("search vc: " + event.getValue());
			search(event.getValue());
			dlContainer.removeAll();
		});
		
		clearSearch.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				searchBox.setValue("");
				itemList.setItems(items);
				dlContainer.removeAll();
			}
		});
		
		// handle resize
		Page page = UI.getCurrent().getPage();
		page.addBrowserWindowResizeListener(event -> {
			resize();
		});
		
		resize();
		// load entities from database
		loadData();
	}
	
	public void resize() {
		// TODO dynamic resize
		// log.info("Resize");

		aceEditor.setWidth("95%");
		aceEditor.setHeight("600px");
		
		itemList.setWidth("100%");
		itemList.setHeight("600px");

		this.splitLayout.setWidth("1400px");
	}
	
	protected void setDetailTitle(String title) {
		this.editorTitle.setText(title);
	}
	
	protected void search(String text) {
		List<T> result = new ArrayList<T>();
		for(T item : items) {
			if(matchListItem(text, item)) {
				result.add(item);
			}
		}
		this.itemList.setItems(result);
	}
	
	protected boolean matchListItem(String text, T item) {
		boolean result = false;

		if(text == null || text.isBlank()) {
			result = true;
		} else {
			String t = text.toLowerCase();
			String lc = item.toString().toLowerCase();
			if(lc.matches(t)) {
				result = true;
			} else if(lc.contains(t)) {
				result = true;
			}
		}
		
		return result;
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
			anchor.setTarget("_blank");
			
			// dlContainer.add(VaadinIcon.DOWNLOAD.create());
			dlContainer.add(anchor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
