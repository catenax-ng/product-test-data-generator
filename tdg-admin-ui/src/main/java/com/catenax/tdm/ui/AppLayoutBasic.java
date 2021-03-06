package com.catenax.tdm.ui;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.Env;
import com.catenax.tdm.security.SecurityUtils;
import com.catenax.tdm.ui.view.AbstractEditor;
import com.catenax.tdm.ui.view.datatemplate.DataTemplateEditor;
import com.catenax.tdm.ui.view.testdatascenario.TestDataScenarioEditor;
import com.catenax.tdm.ui.view.testdatascenario.instance.TestDataScenarioInstanceEditor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;

public class AppLayoutBasic extends AppLayout {

	private static final Logger log = LoggerFactory.getLogger(AppLayoutBasic.class);

	private Map<Tab, Component> tabsToPages = new HashMap<>();
	private Div content = new Div();
	private Tabs tabs = null;
	
	private String logoutUrl = null;

	public AppLayoutBasic() {
		this.init();
	}

	private void init() {
		this.logoutUrl = SecurityUtils.getLogoutUrl();
		
		Anchor logout = new Anchor(logoutUrl, "Logout");
		
		if (!SecurityUtils.isUserLoggedIn()) {
			logout = new Anchor(logoutUrl, "Login");
			this.setContent(logout);
			return;
		}
		
		
		
		DrawerToggle toggle = new DrawerToggle();
		content.setSizeFull();

		H1 title = new H1("Test Data Manager");
		title.getStyle().set("padding", "15px");
		title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

		generateTabs();

		logout.getStyle().set("margin-left", "auto");
        logout.getStyle().set("padding", "15px");
		
		Div right = new Div(new Label(SecurityUtils.getUsername()), logout);
		
		right.getStyle().set("margin-left", "auto");
        right.getStyle().set("padding", "15px");
        
        Image img = new Image("https://catena-x.net/fileadmin/user_upload/logos/logo.svg", "Catena-X");
        img.setHeight("44px");

		this.addToDrawer(this.tabs);
		this.addToNavbar(toggle, img, title, right);
		
		this.setContent(this.content);
		/*
		Page page = UI.getCurrent().getPage();
		page.addBrowserWindowResizeListener(event -> {
			log.info("Resize");
			
			String w = "";
			String h = "";
			
		    content.setWidth(w);
		    content.setHeight(h);
		});
		*/
	}

	private void generateTabs() {
		// Cockpit
		Tab tabCockpit = new Tab(VaadinIcon.BAR_CHART.create(), new Span("Cockpit"));
		Div divCockpit = generateCockpit();
		tabsToPages.put(tabCockpit, divCockpit);

		// Test Data Scenarios
		Tab tabScenarios = new Tab(VaadinIcon.BOOK.create(), new Span("Test Data Scenarios"));
		Div divScenarios = generateScenarios();
		tabsToPages.put(tabScenarios, divScenarios);
		
		// Test Data Scenarios
		Tab tabScenarioInstances = new Tab(VaadinIcon.CAR.create(), new Span("Test Data Instances"));
		Div divScenarioInstances = generateScenarioInstances();
		tabsToPages.put(tabScenarioInstances, divScenarioInstances);

		// Data Templates
		Tab tabTemplates = new Tab(VaadinIcon.COINS.create(), new Span("Test Data Templates"));
		Div divTemplates = generateTemplates();
		tabsToPages.put(tabTemplates, divTemplates);
		
		// Metamodel editor
		Tab tabMetamodel = new Tab(VaadinIcon.BAR_CHART.create(), new Span("Meta Models"));
		Div divMetamodel = generateMetaModels();
		tabsToPages.put(tabMetamodel, divMetamodel);
		
		Tab tabFunctions = new Tab(VaadinIcon.BAR_CHART.create(), new Span("Functions"));
		Div divFunctions = generateFunctions();
		tabsToPages.put(tabFunctions, divFunctions);

		// -------------------------------------------
		content = new Div(/*divCockpit, */divScenarios, divScenarioInstances, divTemplates/*, divMetamodel, divFunctions*/);
		// content.setSizeFull();
		// -------------------------------------------

		tabs = new Tabs(/*tabCockpit, */tabScenarios, tabScenarioInstances, tabTemplates/*, tabMetamodel, tabFunctions*/);
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		tabs.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {
			@Override
			public void onComponentEvent(SelectedChangeEvent event) {
				selectTab(event);
			}
		});

		tabs.setSelectedTab(tabScenarios);
	}

	private void selectTab(SelectedChangeEvent event) {
		Tab tab = event.getSelectedTab();
		selectTab(tab);
	}
	
	private void selectTab(Tab tab) {
		log.info("Select Tab '" + tab.toString() + "'");

		tabsToPages.values().forEach(page -> page.setVisible(false));
		if (tabsToPages.containsKey(tabs.getSelectedTab())) {
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			if(selectedPage instanceof AbstractEditor) {
				((AbstractEditor) selectedPage).resize();
			}
		}
	}

	private Div generateCockpit() {
		Div result = new Div();
		result.setVisible(false);
		
		return result;
	}

	private Div generateScenarios() {
		TestDataScenarioEditor ui = new TestDataScenarioEditor();
		ui.setVisible(true);

		return ui;
	}
	
	private Div generateTemplates() {
		DataTemplateEditor ui = new DataTemplateEditor();
		ui.setVisible(false);
		
		return ui;
	}
	
	private Div generateScenarioInstances() {
		TestDataScenarioInstanceEditor ui = new TestDataScenarioInstanceEditor();
		ui.setVisible(false);
		
		return ui;
	}
	
	private Div generateMetaModels() {
		Div ui = new Div();
		ui.setVisible(false);
		
		return ui;
	}
	
	private Div generateFunctions() {
		Div ui = new Div();
		ui.setVisible(false);
		
		return ui;
	}
}
