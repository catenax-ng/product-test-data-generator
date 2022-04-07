package com.catenax.tdm.metamodel;

import java.util.Iterator;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.resource.TDMResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResourceMetaModelRepository implements MetaModelResourceRepository {

	private static final Logger log = LoggerFactory.getLogger(ResourceMetaModelRepository.class);

	private ObjectMapper objectMapper = buildObjectMapper();

	private static String BASEDIR = "schema";

	private ObjectMapper buildObjectMapper() {
		ObjectMapper om = new ObjectMapper();
		return om;
	}
	
	protected JSONObject resolveSchemaReferences(JSONObject schema, JSONObject defs) throws Exception {
		// log.info("parsing schema for schema references: " + schema.toString());

		Iterator<String> keys = schema.keys();

		while (keys.hasNext()) {
			String key = keys.next();
			Object value = schema.get(key);

			// log.info(" => found key: '" + key + "' := " + value.getClass().getName());

			if ("$ref".equals(key)) {
				String ref = value.toString();
				
				String[] path = null;
				String tref = null;
				
				int iStart = 0;
				
				if (ref.startsWith("schema")) {
					ref = "/" + ref;
					schema.put("$ref", ref);
					
				}
				
				if (ref.startsWith("/schema")) {
					tref = "#/$defs" + ref;
					path = ref.split("/");
					iStart = 1;
				} 
				
				if(path != null && tref != null) {
					// log.info(" ===> resolve $ref '" + ref + "' ===> " + tref + " (" + path.length
					// + ")");
					schema.put("$ref", tref);

					JSONObject def = defs;
					// log.info("START DEF: " + def);

					for (int i = iStart; i < path.length; i++) {
						String p = path[i];
						if (!def.has(p)) {
							JSONObject o = new JSONObject();
							if (i == path.length - 1) {
								String name = path[path.length - 2];
								String version = path[path.length - 1];

								String model = this.getMetamodelAsString(name, version);
								o = new JSONObject(model);
							}
							def.put(p, o);
						}
						def = def.getJSONObject(p);

						// log.info(" ~~~~> add " + p);
					}
				}
			} else if (value instanceof JSONObject) {
				// log.info(" ==> resolve JSONObject ...");
				resolveSchemaReferences((JSONObject) value, defs);
			}
		}

		return schema;
	}

	@Override
	public String getMetamodelAsString(String pMetamodel, String pVersion) throws Exception {
		String fname = BASEDIR + "/" + pMetamodel + "_v" + pVersion + ".json";
		// log.info("LOAD MetaModel: " + fname);
		String result = TDMResourceLoader.resourceToString(fname);
		
		JSONObject schema = new JSONObject(result);
		JSONObject defs = null;

		if (schema.has("$defs")) {
			defs = schema.getJSONObject("$defs");
		} else {
			defs = new JSONObject();
			schema.put("$defs", defs);
		}

		schema = resolveSchemaReferences(schema, defs);
		result = schema.toString();

		return result;
	}

	@Override
	public JSONObject getMetamodel(String pMetamodel, String pVersion) throws Exception {
		/*
		String fname = basedir + "/" + pMetamodel + "_v" + pVersion + ".json";
		InputStream is = TDMResourceLoader.loadResource(fname);
		JSONTokener tokener = new JSONTokener(is);
		JSONObject jsonSchema = new JSONObject(tokener);
		*/
		
		JSONObject jsonSchema = new JSONObject(getMetamodelAsString(pMetamodel, pVersion));
		
		return jsonSchema;
	}

}
