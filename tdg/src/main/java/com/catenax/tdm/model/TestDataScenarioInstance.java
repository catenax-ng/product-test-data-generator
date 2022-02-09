package com.catenax.tdm.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("test_data_scenario_instance")
public class TestDataScenarioInstance implements Serializable {

	private static final long serialVersionUID = -9131780301747832469L;

	@Id
	@JsonIgnore
	private String _id;
	
	private String name;
	private String scenarioId;
	private String content;
	
	public TestDataScenarioInstance() {
		this.name = "<new>";
		this.scenarioId = null;
		this.content = "";
	}

	public TestDataScenarioInstance(String name, String scenarioId, String content) {
		this.name = name;
		this.scenarioId = scenarioId;
		this.content = content;
	}

	public String get_id() {
		return this._id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(String scenarioId) {
		this.scenarioId = scenarioId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
