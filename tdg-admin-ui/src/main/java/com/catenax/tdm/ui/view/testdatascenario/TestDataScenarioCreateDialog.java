package com.catenax.tdm.ui.view.testdatascenario;

import java.util.ArrayList;
import java.util.List;

import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.client.model.TestDataScenario.ScriptStatusEnum;
import com.catenax.tdm.client.model.TestDataScenario.ScriptTypeEnum;
import com.catenax.tdm.ui.dialog.CreateDialog;
import com.vaadin.flow.component.combobox.ComboBox;

public class TestDataScenarioCreateDialog extends CreateDialog<TestDataScenario> {

	private static final long serialVersionUID = -8821137919109313971L;

	public TestDataScenarioCreateDialog() {
		super();
	}

	@Override
	protected void createContent() {
		super.createContent();

		List<TestDataScenario.ScriptTypeEnum> types = new ArrayList<>();
		
		for(TestDataScenario.ScriptTypeEnum st : TestDataScenario.ScriptTypeEnum.values()) {
			types.add(st);
		}
		
		this.fScriptTypeCombo.setItems(types);
		this.fScriptTypeCombo.setValue(TestDataScenario.ScriptTypeEnum.DSL);
		
		this.fScriptTypeCombo.addValueChangeListener(event -> {
			System.out.println("Select Script Type: " + event.getValue());
			// this.fScriptType = event.getValue();
		});
		
		add(fScriptTypeCombo);
	}

	@Override
	public TestDataScenario getData() {
		TestDataScenario result = new TestDataScenario();

		ScriptTypeEnum fScriptType = super.fScriptTypeCombo.getValue();
		if(fScriptType != null) {
			// System.out.println("WHOAAAAAA!!!!");
		} else {
			fScriptType = TestDataScenario.ScriptTypeEnum.DSL;
			System.out.println("ScriptType: " + fScriptType);
		}

		result.setName(fName.getValue().replaceAll(" ", "_"));
		result.setVersion(fVersion.getValue().replaceAll(" ", "."));
		
		result.setScriptStatus(ScriptStatusEnum.PRODUCTIVE);
		
		// result.setScriptType(ScriptTypeEnum.DSL);
		try {
			result.setScriptType(fScriptType);
		} catch (Exception e) {
			System.err.println(e);
			result.setScriptType(ScriptTypeEnum.DSL);
		}
		
		System.out.println("ScriptType result: " + result.getScriptType());
		
		if(ScriptTypeEnum.DSL.equals(result.getScriptType())) {		
			result.setContent("Name: " + result.getName() + ";\nVersion: " + result.getVersion() + ";\n\n");
		} else {
			result.setContent("// javascript \n// Name: " + result.getName() + "\n// Version: " + result.getVersion() + "\n\n");
		}
		
		return result;
	}
}
