package com.catenax.tdm.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.Config;
import com.catenax.tdm.dao.DataTemplateRepository;
import com.catenax.tdm.metamodel.MetamodelRepository;
import com.catenax.tdm.model.DataTemplate;
import com.catenax.tdm.resource.TDMResourceLoader;
import com.catenax.tdm.testdata.TestDataGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDataScenarioFactory {

	private static final class ResultClass {
		protected Object result = null;
	}

	private static final Logger log = LoggerFactory.getLogger(TestDataScenarioFactory.class);

	private MetamodelRepository metamodelRepository;
	private TestDataGenerator testdataGenerator;
	private DataTemplateRepository dataTemplateRepository;
	
	private ResultClass result = new ResultClass();
	private Object manualResult = null;
	
	private Map<String, List<Object>> results = new HashMap<>();
	
	private JSONObject graphQL = new JSONObject();
	
	public static boolean AUTOFIX_ID = true;
	public static String CX_SCHEMA_PREFIX = "https://catenax.com/schema/";
	
	public TestDataScenarioFactory(MetamodelRepository metamodelRepository, TestDataGenerator testdataGenerator) {
		this.metamodelRepository = metamodelRepository;
		this.testdataGenerator = testdataGenerator;
	}
	
	public Object getResult(boolean includeGraphQL) {		
		if(this.manualResult != null) {
			return this.manualResult;
		} else {
			JSONObject o = new JSONObject(results);	
			if(includeGraphQL) {
				generateGraphQL(o);
				o.put("graphql", graphQL);
			}
			this.result.result = o;
			return this.result.result;
		}
	}

	private void generateGraphQL(JSONObject objects) {
		for(String key : objects.keySet()) {
			log.info(" ===> Add to graphql: " + key);
			// TODO: array or object
			JSONArray arr = objects.getJSONArray(key);
			if(arr != null && arr.length() > 0) {	
				Object o = arr.get(0);

				String k = this.getMainKey(key);
				
				String t = "type " + k + " {";
				
				if(o instanceof JSONObject) {
					t += this.generateGraphQLAttribute((JSONObject) o);
				} 
				
				t += " } ";
				
				graphQL.put(key, t);
			}
		}
	}
	
	private String generateGraphQLAttribute(JSONObject object) {
		String result = "";
		
		log.info(" ====> GraphQL for object: " + object.toString());
		for(String key : object.keySet()) {
			Object value = object.get(key);
			log.info(" ====> GrapQL resolve '" + key + "' from type: " + value.getClass().getCanonicalName());
			if(value instanceof JSONObject) {
				String cn = this.camelCase(key);
				result += key + " := " + cn + " ";
				// TODO generate type of cn
				JSONArray arr = new JSONArray();
				arr.put(value);
				JSONObject o = new JSONObject();
				o.put(key, arr);
				generateGraphQL(o);
			} else if(value instanceof String) {
				result += key + " := String ";
			} else {
				result += key + " := Any ";
			}
			
		}
		
		return result;
	}
	
	private String getMainKey(String value) {
		String result = value;
		
		result = result.toLowerCase()
				.replaceAll("/", "")
				.replaceAll("\\.", "")
				.replaceAll(":", "")
				;
		
		/*
		 * String[] kk = key.split("/");
		 * String k = kk[kk.length-1];
		 * k = this.camelCase(k);
		 */
		
		result = this.camelCase(result);
		return result;
	}
	
	private String camelCase(String value) {
		String[] words = value.split("[\\W_]+");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
		    String word = words[i];
		    word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase(); 
		    builder.append(word);
		}
		return builder.toString();
	}
	
	public void flush() {
		this.results.clear();
	}

	public void setResult(Object object) {
		this.manualResult = object;
	}

	public JSONObject getSchema(String name, String version) throws Exception {
		return this.defineSchema(name, version);
	}
	
	public JSONObject defineSchema(String name, String version) throws Exception {
		JSONObject result = this.metamodelRepository.getMetamodel(name, version);
		
		if(AUTOFIX_ID) {
			if(!result.has("$id")) {
				String id = CX_SCHEMA_PREFIX + name + "/" + version;
				result.put("$id", id);
			}
		}
		
		return result;
	}
	
	public JSONObject generateTestData(JSONObject definition) throws Exception {
		Schema schema = SchemaLoader.load(definition);
		
		JSONObject properties = definition.getJSONObject("properties");
		Map<String, Object> p = new HashMap<>();
		
		for(String key : properties.keySet()) {
			Object attr = this.testdataGenerator.generateObject(key, properties.getJSONObject(key), definition);
			p.put(key, attr);
		}
		
		JSONObject o = new JSONObject(p);
		
		if(Config.VALIDATE) {
			try {
				schema.validate(o);
			} catch (Exception e) {				
				if(Config.VALIDATE_EXIT) {
					throw e;
				} else {
					log.info(e.getMessage());
				}
			}
		}
		
		addResult(o, definition);
		
		return o;
	}
	
	public JSONObject generateTestData(JSONObject definition, String templateName, String templateVersion) throws Exception {
		JSONObject o = this.generateTestData(definition);
		JSONObject temp = null;
		
		Optional<DataTemplate> dt = this.dataTemplateRepository.findByNameAndVersion(templateName, templateVersion);
		if(dt.isPresent()) {
			temp = new JSONObject(dt.get().getContent());
		} else {
			// TODO: from resources
			String fname = "datatemplate/" + templateName + "_v" + templateVersion + ".json";
			String result = TDMResourceLoader.resourceToString(fname);
			temp = new JSONObject(result);
		}
		
		if(temp != null) {
			for(String key : temp.keySet()) {
				Object value = temp.get(key);
				//log.info(" ==> Template Attribute: " + key + " := " + value);
				o.put(key, value);
			}
		}
		
		return o;
	}

	public JSONArray generateTestData(JSONObject definition, int count) throws Exception {
		List<JSONObject> instances = new ArrayList<>();
		
		for(int i = 0; i < count; i++) {
			JSONObject o = this.generateTestData(definition);
			instances.add(o);
		}
		
		JSONArray a = new JSONArray(instances);
		return a;
	}
	
	public void validate(JSONObject object, JSONObject definition) {
		Schema schema = SchemaLoader.load(definition);
		schema.validate(object);
	}
	
	public JSONObject clone(JSONObject object, JSONObject definition) {
		JSONObject result = new JSONObject(object.toMap());
		addResult(result, definition);
		return result;
	}
	
	public void addResult(JSONObject object, JSONObject definition) {
		String idAttr1 = "$id";
		String idAttr2 = "$schema";
		String id = null;
		
		if(definition.has(idAttr1)) {		
			id = definition.getString(idAttr1);
		} else if(definition.has(idAttr2)) {		
			id = definition.getString(idAttr2);
		}
		
		if(id != null) {
			// id = "\"" + id + "\"";
			
			List<Object> list = new ArrayList<>();
			if(!this.results.containsKey(id)) {
				this.results.put(id, list);
			} else {
				list = this.results.get(id);
			}
			list.add(object);
		} else {
			ObjectMapper om = new ObjectMapper();
			
			try {
				String pp = definition.toString(); // om.writerWithDefaultPrettyPrinter().writeValueAsString(definition);
				log.error("Failed to retrieve id element for " + pp);
			} catch (Exception e) {
				// TODO
				e.printStackTrace();
			}
		}
	}

	public void setDataTemplateRepository(DataTemplateRepository dataTemplateRepository) {
		this.dataTemplateRepository = dataTemplateRepository;
	}

}
