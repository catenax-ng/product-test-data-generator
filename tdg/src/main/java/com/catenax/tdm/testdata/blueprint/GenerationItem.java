package com.catenax.tdm.testdata.blueprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catenax.tdm.testdata.blueprint.VehicleBlueprint.ItemType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonPropertyOrder(alphabetic = true)
@JsonPropertyOrder({ 
	"comment", 
	"nodeType",
	"modelName", 
	"modelVersion", 
	"useTemplate", 
	"templateName", 
	"templateVersion", 
	"count",
	"useId",
	"values", 
	"children" 
})
public class GenerationItem implements Serializable {
	@JsonProperty("modelName")
	private String modelName = "";

	@JsonProperty("modelVersion")
	private String modelVersion = "";

	@JsonProperty("useTemplate")
	private boolean useTemplate = false;
	
	@JsonProperty("useId")
	private boolean useId = false;

	@JsonProperty("templateName")
	private String templateName = "";

	@JsonProperty("templateVersion")
	private String templateVersion = "";

	@JsonProperty("count")
	private int count = 1;

	@JsonProperty("children")
	private List<GenerationItem> children = new ArrayList<>();

	@JsonProperty("values")
	private Map<String, String> properties = new HashMap<>();

	@JsonProperty("comment")
	private String comment = "";

	@JsonProperty("nodeType")
	private ItemType nodeType = ItemType.PARENT;
	
	@JsonProperty("instanceId")
	private String instanceId = "";
	
	@JsonProperty("code")
	private List<String> code = new ArrayList<>();
	
	@JsonProperty("catenaXId")
	private String catenaXId = null;

	public List<GenerationItem> getChildren() {
		return children;
	}

	public String getComment() {
		return comment;
	}

	public int getCount() {
		return count;
	}

	public String getModelName() {
		return modelName;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public ItemType getNodeType() {
		return nodeType;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateVersion() {
		return templateVersion;
	}

	public boolean isUseId() {
		return useId;
	}

	public boolean isUseTemplate() {
		return useTemplate;
	}

	public void setChildren(List<GenerationItem> children) {
		this.children = children;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public void setNodeType(ItemType nodeType) {
		this.nodeType = nodeType;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}

	public void setUseId(boolean useId) {
		this.useId = useId;
	}

	public void setUseTemplate(boolean useTemplate) {
		this.useTemplate = useTemplate;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public List<String> getCode() {
		return code;
	}

	public void setCode(List<String> code) {
		this.code = code;
	}

	public String getCatenaXId() {
		return catenaXId;
	}

	public void setCatenaXId(String catenaXId) {
		this.catenaXId = catenaXId;
	}

}
