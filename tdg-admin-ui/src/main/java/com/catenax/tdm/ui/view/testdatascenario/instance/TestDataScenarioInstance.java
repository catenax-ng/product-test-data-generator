package com.catenax.tdm.ui.view.testdatascenario.instance;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class TestDataScenarioInstance {

	private String name = null;
	private String scenarioName = null;
	private String scenarioVersion = null;
	
	public TestDataScenarioInstance() {

	}
	
	public TestDataScenarioInstance(String name, String scenarioName, String scenarioVersion) {
		this.name = name;
		this.scenarioName = scenarioName;
		this.scenarioVersion = scenarioVersion;
	}

	public TestDataScenarioInstance(Object json) {
		LinkedTreeMap<?,?> ltm = (LinkedTreeMap<?,?>) json;

		this.name = ltm.get("name").toString();
		this.scenarioName = ltm.get("scenario").toString();
		this.scenarioVersion = ltm.get("version").toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String getScenarioVersion() {
		return scenarioVersion;
	}

	public void setScenarioVersion(String scenarioVersion) {
		this.scenarioVersion = scenarioVersion;
	}

	@Override
	public String toString() {
		return name; //  + "(" + scenarioName + " " + scenarioVersion + ")";
	}

}
