package com.catenax.tdm.metamodel;

import org.json.JSONObject;

public interface MetaModelResourceRepository {
	
	public String getMetamodelAsString(String pMetamodel, String pVersion) throws Exception;

	public JSONObject getMetamodel(String pMetamodel, String pVersion) throws Exception;
	
}
