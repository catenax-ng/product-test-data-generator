package com.catenax.tdm.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("test_data_scenario")
public class TestDataScenario implements Serializable {

	private static final long serialVersionUID = -9131780301747832869L;

	public static enum TestDataScenarioType {
		DSL,
		JavaScript;
	};
	
	public static enum TestDataScenarioStatus {
		DRAFT,
		PRODUCTIVE,
		DECOMISSIONED
	};
	
	@Id
	@JsonIgnore
	private String _id;
	
	private String name;
	private String version;
	private TestDataScenarioType scriptType;
	private TestDataScenarioStatus scriptStatus;
	private String content;
	
	public TestDataScenario() {
		this.name = "<new>";
		this.version = "0.1";
		this.scriptType = TestDataScenarioType.DSL;
		this.scriptStatus = TestDataScenarioStatus.DRAFT;
		this.content = "";
	}

	public TestDataScenario(String name, String version, TestDataScenarioType scriptType, TestDataScenarioStatus scriptStatus, String content) {
		this.name = name;
		this.version = version;
		this.scriptType = scriptType;
		this.scriptStatus = scriptStatus;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public TestDataScenarioType getScriptType() {
		return scriptType;
	}

	public void setScriptType(TestDataScenarioType scriptType) {
		this.scriptType = scriptType;
	}

	public TestDataScenarioStatus getScriptStatus() {
		return scriptStatus;
	}

	public void setScriptStatus(TestDataScenarioStatus scriptStatus) {
		this.scriptStatus = scriptStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
