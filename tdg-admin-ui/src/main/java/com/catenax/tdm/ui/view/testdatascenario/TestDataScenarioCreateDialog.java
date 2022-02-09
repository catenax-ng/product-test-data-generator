package com.catenax.tdm.ui.view.testdatascenario;

import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.client.model.TestDataScenario.ScriptStatusEnum;
import com.catenax.tdm.client.model.TestDataScenario.ScriptTypeEnum;
import com.catenax.tdm.ui.dialog.CreateDialog;

public class TestDataScenarioCreateDialog extends CreateDialog<TestDataScenario> {

	public TestDataScenarioCreateDialog() {
		super();
	}

	@Override
	public TestDataScenario getData() {
		TestDataScenario result = new TestDataScenario();

		result.setName(fName.getValue().replaceAll(" ", "_"));
		result.setVersion(fVersion.getValue().replaceAll(" ", "."));
		
		result.setScriptStatus(ScriptStatusEnum.PRODUCTIVE);
		result.setScriptType(ScriptTypeEnum.DSL);
		
		result.setContent("Name: " + result.getName() + ";\nVersion: " + result.getVersion() + ";\n\n");
		
		return result;
	}
}
