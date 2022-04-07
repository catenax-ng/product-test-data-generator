package com.catenax.tdm.metamodel;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SemanticMetaModelRepository extends ResourceMetaModelRepository {

	private static final Logger log = LoggerFactory.getLogger(SemanticMetaModelRepository.class);

	private ObjectMapper objectMapper = buildObjectMapper();

	private ObjectMapper buildObjectMapper() {
		ObjectMapper om = new ObjectMapper();
		return om;
	}

	@Override
	public String getMetamodelAsString(String pMetamodel, String pVersion) throws Exception {
		return super.getMetamodelAsString(pMetamodel, pVersion);
	}

	@Override
	public JSONObject getMetamodel(String pMetamodel, String pVersion) throws Exception {
		return super.getMetamodel(pMetamodel, pVersion);
	}

}
