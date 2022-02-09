package com.catenax.tdm.ui.view.testdatascenario.instance;

import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.client.model.TestDataScenario.ScriptStatusEnum;
import com.catenax.tdm.client.model.TestDataScenario.ScriptTypeEnum;
import com.catenax.tdm.ui.dialog.CreateDialog;

public class TestDataScenarioInstanceCreateDialog extends CreateDialog<TestDataScenarioInstance> {

	public TestDataScenarioInstanceCreateDialog() {
		super();
	}

	@Override
	public TestDataScenarioInstance getData() {
		TestDataScenarioInstance result = new TestDataScenarioInstance();

		result.setName(fName.getValue().replaceAll(" ", "_"));
		result.setScenarioVersion(fVersion.getValue().replaceAll(" ", "."));
		
		
		// result.setContent("Name: " + result.getName() + ";\nVersion: " + result.getVersion() + ";\n\n");
		
		return result;
	}

	@Override
	protected void createContent() {
		// TODO: modify accordingly
		
		super.createContent();
	}
}
