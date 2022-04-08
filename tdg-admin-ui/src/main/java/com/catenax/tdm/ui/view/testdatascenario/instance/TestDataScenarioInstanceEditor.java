package com.catenax.tdm.ui.view.testdatascenario.instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.catenax.tdm.client.ApiException;
import com.catenax.tdm.client.model.DataTemplate;
import com.catenax.tdm.ui.dialog.YesNoDialog;
import com.catenax.tdm.ui.view.AbstractEditor;
import com.catenax.tdm.util.JsonUtil;
import com.google.gson.internal.LinkedTreeMap;

import io.github.ciesielskis.AceMode;

public class TestDataScenarioInstanceEditor extends AbstractEditor<TestDataScenarioInstance> {
	
	public TestDataScenarioInstanceEditor() {
		super(new TestDataScenarioInstanceCreateDialog());
		
		this.postInit();
	}
	
	private void postInit() {
		this.aceEditor.setReadOnly(true);
		this.getRightToolbar().removeAll();
		this.aceEditor.setMode(AceMode.json);
		
		this.btnNew.setVisible(false);
	}

	@Override
	protected void selectItem(TestDataScenarioInstance scenario) {
		this.selected = scenario;
		
		 try {
			Object o = this.getClient().getTestdataScenarioInstanceApi().instantiateTestdataScenarioUsingGET(
					scenario.getScenarioName(), scenario.getScenarioVersion(), scenario.getName(), 
					false, false);
			
			String json = JsonUtil.prettyPrint(JsonUtil.toJson((LinkedTreeMap<?,?>)o));
			
			this.aceEditor.setValue(json);
			this.aceEditor.setMode(AceMode.json);
			this.setDownloadableContent(json);
			
			this.setDetailTitle(this.selected.toString());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void loadData() {
		this.items = new ArrayList<>();
		try {
			List<Object> raw = (List<Object>) this.getClient().getTestdataScenarioInstanceApi().listTestdataScenarioInstancesUsingGET("*", "*", "*");
			
			for(Object o : raw) {
				TestDataScenarioInstance i = new TestDataScenarioInstance(o);
				this.items.add(i);
			}
			
			ArrayList<TestDataScenarioInstance> list = new ArrayList<>();
			list.addAll(items);
			Collections.sort(list, new Comparator<TestDataScenarioInstance>() {
				@Override
				public int compare(TestDataScenarioInstance o1, TestDataScenarioInstance o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});
			
			items = list;
		} catch (ApiException e) {
			e.printStackTrace();
		}
		this.itemList.setItems(items);
	}

	@Override
	protected void createNew(TestDataScenarioInstance result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void deleteSelection() {
		if(this.selected != null) {
			YesNoDialog dia = new YesNoDialog(
					"This will delete '" + this.selected.getName() + "' permanently for all users and cannot be restored. Are you sure?",
					this::confirmDelete
			);
			dia.open();
		}
	}
	
	private void confirmDelete() {
		try {
			if(this.selected != null) {
				this.getClient().getTestdataScenarioInstanceApi().deleteTestdataScenarioInstanceUsingDELETE(
						this.selected.getScenarioName(), 
						this.selected.getScenarioVersion(), 
						this.selected.getName());
				this.selected = null;
				this.aceEditor.setValue("");
				this.dlContainer.removeAll();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.loadData();
	}

	@Override
	protected void saveCurrent() {
		// TODO Auto-generated method stub
		
	}

}
