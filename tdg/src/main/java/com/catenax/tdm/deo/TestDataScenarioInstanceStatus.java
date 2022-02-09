package com.catenax.tdm.deo;

import com.catenax.tdm.model.TestDataScenario;
import com.catenax.tdm.model.TestDataScenarioInstance;

public class TestDataScenarioInstanceStatus {

	public static TestDataScenarioInstanceStatus fromInstance(TestDataScenario scenario, TestDataScenarioInstance instance) {
		TestDataScenarioInstanceStatus result = new TestDataScenarioInstanceStatus();
		
		result.scenario = scenario.getName();
		result.version = scenario.getVersion();
		result.name = instance.getName();

		return result;
	}
	
	private String scenario;
	private String version;
	private String name;

	private TestDataScenarioInstanceStatus() {

	}

	public String getScenario() {
		return scenario;
	}

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

}
