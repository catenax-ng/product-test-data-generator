package com.catenax.tdm.testdata.blueprint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ 
	"name",
	"version",
	"parent"
})
public class VehicleBlueprint {
	
	public static enum ItemType {
		NULL,
		PARENT,
		CHILD;
	}
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("version")
	private String version;

	/** The parent. */
	@JsonProperty("parent")
	private GenerationItem parent = null;

	/**
	 * Instantiates a new blueprint.
	 */
	public VehicleBlueprint() {
		this.parent = new GenerationItem();
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

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public GenerationItem getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(GenerationItem parent) {
		this.parent = parent;
	}

}
