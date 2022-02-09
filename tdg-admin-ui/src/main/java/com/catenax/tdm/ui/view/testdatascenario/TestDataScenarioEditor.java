package com.catenax.tdm.ui.view.testdatascenario;

import java.util.Base64;
import java.util.Base64.Encoder;

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
				String code = aceEditor.getValue();				
				runScript(code);
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
		selected = scenario;
		this.itemList.setValue(scenario);
		aceEditor.setValue(_fixContent(scenario.getContent()));
		dlContainer.removeAll();
	}

	@Override
	protected void loadData() {
		this.itemList.setItems(this.getClient().getTestScenarioDefinitions());
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
	
	private void runScript(String code) {
		try {
			clearDownloadableContent();
			
			Encoder encoder = Base64.getEncoder();
			
			String script = new String(encoder.encode(code.getBytes()));

			Object result = getClient().getTestdataScenarioInstanceApi().instantiateTestdataScenarioRawUsingPOST(script);

			LinkedTreeMap<?,?> ltm = (LinkedTreeMap<?,?>) result;
			Gson gson = new Gson();
			String js = gson.toJsonTree(ltm).getAsJsonObject().toString();
			
			ObjectMapper om = new ObjectMapper();
			
			Object jsObj = new JSONObject(js); // om.readValue(js, JSONArray.class);	
			System.out.println(jsObj.toString());

			String res = om.writerWithDefaultPrettyPrinter().writeValueAsString(om.readTree(jsObj.toString()));
			System.out.println(res);
			aceResult.setValue(res);
			
			setDownloadableContent(res);
		} catch (Exception e) {
			e.printStackTrace();
			aceResult.setValue("ERROR: " + e.getMessage());
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
