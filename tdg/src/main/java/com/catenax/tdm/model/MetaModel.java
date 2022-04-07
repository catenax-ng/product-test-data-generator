package com.catenax.tdm.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("test_meta_model")
public class MetaModel implements Serializable {
	
	@Id
	@JsonIgnore
	private String _id;
	
	private String name;
	
	private String version;
	
	private String content;

	public MetaModel() {
		this.name = "";
		this.version = "";
		this.content = "";
	}

	public String get_id() {
		return _id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
