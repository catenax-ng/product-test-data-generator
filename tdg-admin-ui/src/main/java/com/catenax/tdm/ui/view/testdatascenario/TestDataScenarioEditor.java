package com.catenax.tdm.ui.view.testdatascenario;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONObject;

import com.catenax.tdm.client.ApiException;
import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.client.model.TestDataScenario.ScriptStatusEnum;
import com.catenax.tdm.client.model.TestDataScenario.ScriptTypeEnum;
import com.catenax.tdm.ui.dialog.YesNoDialog;
import com.catenax.tdm.ui.view.AbstractEditor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

import io.github.ciesielskis.AceMode;

public class TestDataScenarioEditor extends AbstractEditor<TestDataScenario> {

	public TestDataScenarioEditor() {
		super(new TestDataScenarioCreateDialog());
		
		initAddon();
	}

	private void initAddon() {
		Button btnRun = new Button(VaadinIcon.AIRPLANE.create());
		Button btnNewInstance = new Button(VaadinIcon.NEWSPAPER.create());
		
		btnRun.setText("Run");
		btnNewInstance.setText("Instanciate");

		getRightToolbar().add(btnRun);
		getRightToolbar().add(btnNewInstance);
		
		btnRun.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				dlContainer.removeAll();
				String code = aceEditor.getValue();	
				ScriptTypeEnum st = ScriptTypeEnum.DSL;
				if(selected != null) {
					st = selected.getScriptType();
				}
				runScript(st, code);
			}
		});
		
		btnNewInstance.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				openNewInstance(selected);
			}
		});

		String mode = "ace/mode/tdgdsl"; // "http://localhost:8090/mode-tdgdsl.js"; // "ace/mode/" + "tdgdsl";
		this.aceEditor.getElement().setAttribute("mode", mode);
		//this.aceEditor.setMode(AceMode.json);
	}

	@Override
	protected void selectItem(TestDataScenario scenario) {
		this.selected = scenario;
		System.out.println("Select Item: " + selected.getName() + " " + selected.getScriptType());
		this.itemList.setValue(scenario);
		
		if(scenario == null || scenario.getScriptType() == null || scenario.getScriptType().equals(ScriptTypeEnum.DSL)) {
			this.aceEditor.setMode(AceMode.text);
		} else if(scenario.getScriptType().equals(ScriptTypeEnum.JAVASCRIPT)) {
			this.aceEditor.setMode(AceMode.javascript);
		}
		
		this.setDetailTitle(this.selected.toString());
		
		this.aceEditor.setValue(_fixContent(scenario.getContent()));
		this.dlContainer.removeAll();
	}

	@Override
	protected void loadData() {
		ArrayList<TestDataScenario> list = new ArrayList<>();
		list.addAll(this.getClient().getTestScenarioDefinitions());
		Collections.sort(list, new Comparator<TestDataScenario>() {
			@Override
			public int compare(TestDataScenario o1, TestDataScenario o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		
		items = list;
		this.itemList.setItems(items);
	}

	@Override
	protected void createNew(TestDataScenario result) {
		if(result.getName().strip().length() == 0 || result.getVersion().strip().length() == 0) {
			return;
		}

		try {
			this.getClient().getTestdataScenarioApi().createTestdataScenarioUsingPOST(result);
			loadData();
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void deleteSelection() {
		if(this.selected != null) {
			YesNoDialog dia = new YesNoDialog(
					"This will delete '" + this.selected.toString() + "' permanently for all users and cannot be restored. Are you sure?",
					this::confirmDelete
			);
			dia.open();
		}
	}
	
	private void confirmDelete() {
		if(this.selected != null) {
			try {
				this.getClient().getTestdataScenarioApi().deleteTestdataScenarioUsingDELETE(selected);		
				
				this.selected = null;

				this.itemList.clear();
				
				this.aceEditor.setValue("");
				this.dlContainer.removeAll();
				
				loadData();
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void saveCurrent() {
		if(this.selected != null) {
			this.selected.setContent(this.aceEditor.getValue());
			System.out.println(selected);
			
			if(selected.getScriptType() == null) {
				selected.setScriptType(ScriptTypeEnum.DSL);
			}
			
			if(selected.getScriptStatus() == null) {
				selected.setScriptStatus(ScriptStatusEnum.PRODUCTIVE);
			}
			
			try {
				this.getClient().getTestdataScenarioApi().updateTestdataScenarioContentUsingPUT(
						selected.getScriptType().getValue(), 
						selected.getScriptStatus().getValue(), 
						selected.getName(), 
						selected.getVersion(), 
						_fixContent(selected.getContent())
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void runScript(ScriptTypeEnum scriptType, String code) {
		try {
			clearDownloadableContent();
			
			Encoder encoder = Base64.getEncoder();
			
			String script = new String(encoder.encode(code.getBytes()));

			Object result = getClient().getTestdataScenarioInstanceApi().instantiateTestdataScenarioRawUsingPOST(scriptType, script);

			LinkedTreeMap<?,?> ltm = (LinkedTreeMap<?,?>) result;
			Gson gson = new Gson();
			String js = gson.toJsonTree(ltm).getAsJsonObject().toString();
			
			ObjectMapper om = new ObjectMapper();
			
			Object jsObj = new JSONObject(js); // om.readValue(js, JSONArray.class);	

			String res = om.writerWithDefaultPrettyPrinter().writeValueAsString(om.readTree(jsObj.toString()));
			setDownloadableContent(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void openNewInstance(TestDataScenario scenario) {
		if(scenario != null) {
			NewTestDataScenarioInstanceDialog dia = new NewTestDataScenarioInstanceDialog(this.getClient(), scenario);
			dia.open();
		}
	}
	
	private void createNewInstance() {
		
	}
}
