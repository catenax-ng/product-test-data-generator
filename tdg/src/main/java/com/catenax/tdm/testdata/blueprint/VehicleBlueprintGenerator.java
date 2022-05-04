package com.catenax.tdm.testdata.blueprint;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.script.Bindings;
import javax.script.ScriptEngineManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.model.DataTemplate;
import com.catenax.tdm.scenario.TestDataScenarioFactory;
import com.catenax.tdm.testdata.TestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VehicleBlueprintGenerator {

	public static final String DEFAULT_ID_FIELD = "catenaXId";
	private static final boolean ELIMINATE_DUPLICATES = true;

	private static final Logger log = LoggerFactory.getLogger(VehicleBlueprintGenerator.class);

	private TestDataScenarioFactory scenario = null;
	private TestDataFactory dataFactory = new TestDataFactory();
	
	private ScriptEngineManager engineManager = new ScriptEngineManager();
	private javax.script.ScriptEngine engine = engineManager.getEngineByName("nashorn");
	private Bindings bindings = this.engine.createBindings();

	private Map<String, JSONObject> ids = new HashMap<>();
	private Map<String, String> constants = new HashMap<>();
	private Map<String, String> vars = new HashMap<>();
	
	private Map<String, JSONObject> takenCatenaXIds = new HashMap<>();

	public VehicleBlueprintGenerator(TestDataScenarioFactory scenario) {
		this.scenario = scenario;

		bindings.put("log", log);		
		bindings.put("dataFactory", dataFactory);
		bindings.put("rand", dataFactory);		
		bindings.put("scenario", scenario);
		bindings.put("vehicleItem", this);
		bindings.put("CONST", constants);
		bindings.put("VARS", this.ids);
	}
	
	public JSONArray generateFromVehicleTemplate(String name, String version) {
		JSONArray result = new JSONArray();

		scenario.addResult(result, scenario.getTestDataContainerDefinition());

		Optional<DataTemplate> dt = scenario.getDataTemplateRepository().findByNameAndVersion(name, version);
		if (dt.isPresent()) {
			JSONObject definition = new JSONObject();
			definition.put("$id", "https://catenax.io/schema/VehicleBlueprint/1.0.0");

			ObjectMapper om = new ObjectMapper();

			try {
				VehicleBlueprint blueprint = om.readValue(dt.get().getContent(), VehicleBlueprint.class);

				// Traverse
				GenerationItem parentItem = null;
				GenerationItem item = blueprint.getParent();
				
				for(String key : item.getProperties().keySet()) {
					String val = item.getProperties().get(key);
					
					String value = this.resolveValue(val, null, null).toString();
					
					log.info("PUT CONST " + key + " := " + value);
					constants.put(key, value);
					
					bindings.put(key, value);
				}
				
				bindings.put("CONST", constants);				
				
				generateParent(item, parentItem);

				String bpString = om.writeValueAsString(blueprint);

				JSONObject bp = new JSONObject(bpString);

				scenario.addResult(bp, definition);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			log.error("Failed to load template '" + name + "' version " + version);
		}

		return result;
	}

	public JSONArray generateParent(GenerationItem item, GenerationItem parent) {
		JSONArray result = new JSONArray();
		try {
			ObjectMapper om = new ObjectMapper();
			
			for (int i = 0; i < item.getCount(); i++) {
				String cxId = null;
				
				if(item.getCatenaXId() == null) {
					cxId = dataFactory.getUuid();
				} else {
					cxId = this.resolveValue(item.getCatenaXId(), null, null).toString();
				}

				bindings.put("catenaXId", cxId);
				
				JSONObject itemContainer = null;
				
				if(this.takenCatenaXIds.containsKey(cxId)) {
					itemContainer = this.takenCatenaXIds.get(cxId);
				} else {				
					itemContainer = scenario.generateTestDataContainer(cxId);
				}
				
				JSONObject gc = generateChild(item, parent, itemContainer, null);
				result.put(gc);
				this.takenCatenaXIds.put(cxId, itemContainer);
				
				// Generate AAS Template
				if(scenario.getAasTemplate() != null && "SerialPartTypization".equals(item.getModelName())) {
					generateAAS(item, om, cxId, itemContainer, gc);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	private void generateAAS(GenerationItem item, ObjectMapper om, String cxId, JSONObject itemContainer,
			JSONObject gc) {
		JSONObject aas = null;					
		JSONObject aasDef = null;
		
		String content = scenario.getAasTemplate().get().getContent();
		
		try {
			aas = new JSONObject(content);
			aasDef = new JSONObject();					
			aasDef.put("$id", "https://catenax.io/schema/AAS/3.0");
			
			String descr = gc.getJSONObject("partTypeInformation").getString("nameAtManufacturer");
			
			String ident = cxId;
			String ids = descr.toLowerCase().replaceAll(" ", "_") + ".asm";
			
			aas.put("identification", ident);
			aas.put("idShort", ids);
			
			String gaId = cxId.replaceAll("urn:uuid:", "urn:twin:com.tsystems#");
			
			aas.getJSONObject("globalAssetId").remove("value");
			aas.getJSONObject("globalAssetId").put("value", new JSONArray());
			aas.getJSONObject("globalAssetId").getJSONArray("value").put(gaId);

			aas.getJSONArray("description").getJSONObject(0).put("text", descr);
			//log.info("Description: " + aas.get("description").getClass().getCanonicalName());
			
			JSONObject specificAssetTemplate1 = new JSONObject(aas.getJSONArray("specificAssetIds").get(0).toString());
			JSONObject specificAssetTemplate2 = new JSONObject(aas.getJSONArray("specificAssetIds").get(1).toString());
			aas.remove("specificAssetIds");
			
			JSONArray specificAssets = new JSONArray();
			
			String v1 = gc.getJSONArray("localIdentifiers").getJSONObject(2).getString("value");
			String v2 = gc.getJSONArray("localIdentifiers").getJSONObject(1).getString("value");
			
			specificAssetTemplate1.put("value", v1);
			specificAssetTemplate2.put("value", v2);
			
			specificAssets.put(specificAssetTemplate1);
			specificAssets.put(specificAssetTemplate2);
			
			aas.put("specificAssetIds", specificAssets);
			
			JSONObject subModelTemplate = new JSONObject(aas.getJSONArray("submodelDescriptors").get(0).toString());
			// aas.remove("submodelDescriptors");
			
			JSONArray subModels = new JSONArray();
			
			for(GenerationItem gi : item.getChildren()) {
				SubmodelDescriptor sd = SubmodelDescriptor.create(gi, gc);
				subModels.put(new JSONObject(om.writeValueAsString(sd)));
			}
			
			aas.put("submodelDescriptors", subModels);
			scenario.addToContainer(itemContainer, aas, aasDef);
		} catch (Exception e) {
			log.error(e.getMessage());
			// log.error(content);
			// log.error("AAS: " + aas + " // " + aasDef);
		}
	}

	public JSONObject generateChild(GenerationItem item, GenerationItem parent, JSONObject itemContainer, JSONObject parentTd) {
		try {
			JSONObject td = null;
			JSONObject schema = null;

			if (!item.getModelName().isBlank() && !item.getModelVersion().isBlank()) {
				schema = scenario.defineSchema(item.getModelName(), item.getModelVersion());

				if (item.isUseTemplate()) {
					td = scenario.generateFromTemplate(schema.getString("$id"), item.getTemplateName(),
							item.getTemplateVersion()); 
				} else {
					td = scenario.generateTestData(schema);
				}
			} else {
				td = new JSONObject();
				schema = new JSONObject();
				schema.put("$id", "PlainObject");
			}

			if (td != null) {
				bindings.put("item", td);
				bindings.put("parent", parentTd);
				bindings.put("container", itemContainer);
				
				if (item.isUseId()) {
					td.put(DEFAULT_ID_FIELD, itemContainer.get(DEFAULT_ID_FIELD));
				}

				for (String key : item.getProperties().keySet()) {
					String val = item.getProperties().get(key);

					resolvePropertySetting(key, val, item, td, parentTd);
				}
				
				boolean duplicate = false;
				
				if(ELIMINATE_DUPLICATES) {
					String sid = schema.getString("$id");
					for(String key : itemContainer.keySet()) {
						if(key.equals(sid)) {
							duplicate = true;
							break;
						}
					}
				}

				if (schema != null && !duplicate) {
					scenario.addToContainer(itemContainer, td, schema);
				}

				if (!item.getInstanceId().isBlank()) {
					ids.put(item.getInstanceId(), td);
				}
			}
			
			JSONArray subparents = new JSONArray();
			JSONArray children = new JSONArray();

			for (GenerationItem child : item.getChildren()) {
				switch (child.getNodeType()) {
				case PARENT: {
					JSONArray sp = generateParent(child, item);
					for (int i=0; i < sp.length(); i++) {
					    subparents.put(sp.getJSONObject(i));
					}
					break;
				}
				case CHILD: {
					JSONObject cr = generateChild(child, item, itemContainer, td);
					children.put(cr);
					break;
				}
				default:
					// NULL Node, do nothing
				}
			}
			
			if(item.getCode().size() > 0) {
				bindings.put("item", td);
				// bindings.put("parent", parentTd);
				bindings.put("container", itemContainer);
				bindings.put("children", children);
				bindings.put("subparents", subparents);
				
				for (String code : item.getCode()) {
					Object codeResult = engine.eval(code, bindings);
					// log.info("CODE '" + code + "' := " + codeResult);
				}
			}
			
			// log.info("RETURN CHILD: " + td);
			return td;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public JSONObject getById(String id) {
		return this.ids.get(id);
	}
	
	public void push(String id, JSONObject val) {
		log.info("PUSH: " + id + " := " + val);
		this.ids.put(id, val);
	}
	
	public JSONObject get(String id) {	
		JSONObject result = this.ids.get(id);
		log.info("GET " + id + " := " + result);
		return result;
	}

	private Object resolveValue(String value, JSONObject item, JSONObject parent) {
		// ToDo make parent values accessible later
		Object o = value;
		
		try {
			if(value.startsWith("#")) {
				// log.info("HERE WE GO");
				String[] tree = value.split("\\.");
				String id = tree[0];
				String property = tree[tree.length-1];
				id = id.substring(1);
				Object element = this.ids.get(id);
				// log.info(" retrieve '" + id + "' " + element.toString());
				
				for(int i = 1; i < tree.length - 1; i++) {
					String k = tree[i];
					String var = k;
					
					if (element instanceof JSONObject) {
						element = ((JSONObject) element).get(var);
					} else if (element instanceof JSONArray) {
						// log.error("Cannot resolve json array");
					} else {
						// log.error("Cannot resolve object: " + o.getClass().getCanonicalName());
					}
				}
				
				if (element instanceof JSONObject) {
					o = ((JSONObject) element).get(property);
				} else if (element instanceof JSONArray) {
					// log.error("Cannot resolve json array");
				} else {
					// log.error("Cannot resolve object: " + o.getClass().getCanonicalName());
				} 
			} else if(value.startsWith("!")) {
				String expr = value.substring(1);

				o = engine.eval(expr, bindings);
			} else if(value.startsWith("$")) {
				String key = value.substring(1);
				// log.info("RESOLVE VALUE: " + key);
				
				String[] levels = key.split("\\.");
				String field = levels[levels.length - 1];

				// log.info("Field: " + field);
				o = item;
				for (int i = 0; i < levels.length - 1; i++) {
					String k = levels[i];

					if (k.contains("[")) {
						String var = k.substring(0, k.indexOf("["));
						String index = k.substring(k.indexOf("[") + 1, k.indexOf("]"));

						if (o instanceof JSONObject) {
							o = ((JSONObject) o).getJSONArray(var).get(Integer.valueOf(index));
						} else if (o instanceof JSONArray) {
							// log.error("Cannot resolve json array");
						} else {
							// log.error("Cannot resolve object: " + o.getClass().getCanonicalName() + "::" + o.toString());
						}

					} else {
						o = ((JSONObject) o).get(k);
					}
				}

				if (o instanceof JSONObject) {
					o = ((JSONObject) o).get(field);
				} else {
					// log.error("Cannot resolve object: " + o.getClass().getCanonicalName() + "::" + o.toString());
				}
			}
		} catch (Exception e) {
			// log.error(e.getMessage(), e);
			o = e.getMessage();
		}
		
		return o;
	}
	
	private void resolvePropertySetting(String key, String val, GenerationItem item, JSONObject result, JSONObject parent) {
		Object o = result;

		try {
			String[] levels = key.split("\\.");
			String field = levels[levels.length - 1];

			for (int i = 0; i < levels.length - 1; i++) {
				String k = levels[i];

				if (k.contains("[")) {
					String var = k.substring(0, k.indexOf("["));
					String index = k.substring(k.indexOf("[") + 1, k.indexOf("]"));

					if (o instanceof JSONObject) {
						o = ((JSONObject) o).getJSONArray(var).get(Integer.valueOf(index));
					} else if (o instanceof JSONArray) {
						// log.error("Cannot resolve json array");
					} else {
						// log.error("Cannot resolve object: " + o.getClass().getCanonicalName());
					}

				} else {
					o = ((JSONObject) o).get(k);
				}
			}
			
			Object finalVal = resolveValue(val, result, parent);
			if (o instanceof JSONObject) {
				((JSONObject) o).put(field, finalVal);
			} else {
				// log.error("Cannot resolve object: " + o.getClass().getCanonicalName());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public Object clearAprChildren(JSONObject apr) {
		Object result = null;
		
		if(apr.has("childParts")) {
			result = apr.get("childParts");
			apr.remove("childParts");
		}
		
		return result;
	}
	
	public void addAprChild(JSONObject apr, JSONObject child) {
		JSONArray arr = new JSONArray();
		
		if(apr.has("childParts")) {
			arr = apr.getJSONArray("childParts");
		}
		
		arr.put(child);
		apr.put("childParts", arr);
	}
	
	public void replaceAprChildren(JSONObject apr, JSONArray children) {
		Object tmp = this.clearAprChildren(apr);
		JSONObject tmpl = null;
		if(tmp instanceof JSONObject) {
			tmpl = (JSONObject) tmp;
		} else if(tmp instanceof JSONArray) {
			tmpl = ((JSONArray) tmp).getJSONObject(0);
		}
		if(tmpl != null && children != null) {
			String cxId = apr.getString("catenaXId");
			JSONArray aprChildren = new JSONArray();
			
			for(int i = 0; i < children.length(); i++) {
				JSONObject item = children.getJSONObject(i);
				String cxChildId = item.getString("catenaXId");
				// log.info("ADD child: " + cxChildId + " to " + cxId);
				
				JSONObject child = new JSONObject(tmpl.toString());
				child.put("childCatenaXId", cxChildId);
				
				aprChildren.put(child);				
			}
			
			apr.put("childParts", aprChildren);
		}
	}
	
	public void var(String id, String val) {
		// log.info("VAR PUT " + id + " := " + val);
		vars.put(id, val);
	}
	
	public String var(String id) {
		String result = vars.get(id);
		// log.info("VAR GET " + id + " := " + result);
		return result;
	}

}
