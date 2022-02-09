package com.catenax.tdm.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("test_data_template")
public class DataTemplate implements Serializable {
	private static final long serialVersionUID = -9131880301747832869L;

	@Id
	@JsonIgnore
	private String _id;
	private String name;
	private String version;
	private String content;

	public DataTemplate() {

	}

	public DataTemplate(String name, String version, String content) {
		this.name = name;
		this.version = version;
		this.content = content;
	}
	
	public DataTemplate(String name, String version, JSONObject content) {
		this.name = name;
		this.version = version;
		this.content = content.toString();
	}
	
	public DataTemplate(String name, String version, JSONArray content) {
		this.name = name;
		this.version = version;
		this.content = content.toString();
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
