package com.catenax.tdm.testdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluttercode.datafactory.impl.DataFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.catenax.tdm.metamodel.MetaModelResourceRepository;

public class SimpleTestDataGenerator implements TestDataGenerator {

	private static final Logger log = LoggerFactory.getLogger(SimpleTestDataGenerator.class);

	private static final TestDataFactory dataFactory = new TestDataFactory();

	@Autowired
	private MetaModelResourceRepository metamodelRepository;

	@Override
	public Object generateObject(String name, JSONObject pDefinition, JSONObject jsonSchema) {
		// log.info(" => Generate for attribute '" + name + "' := " + pDefinition);
		// log.info("RESOLVE attr '" + name + "' (" + jsonSchema.getString("$id") + ")");}

		Object result = null;

		if (pDefinition.has("type")) {
			if (pDefinition.has("enum")) {
				// log.info("-= RESOLVE ENUM " + pDefinition + " =-");
				JSONArray valArr = pDefinition.getJSONArray("enum");
				result = resolveEnum(valArr);
			} else {
				result = resolveType(name, pDefinition, jsonSchema);
			}
		} else if (pDefinition.has("$ref")) {
			result = resolveRef(name, pDefinition, jsonSchema, result);
		} else if (pDefinition.has("oneOf")) {
			JSONArray types = pDefinition.getJSONArray("oneOf");
			result = resolveOption(name, jsonSchema, types);
		} else if (pDefinition.has("anyOf")) {
			JSONArray types = pDefinition.getJSONArray("anyOf");
			result = resolveOption(name, jsonSchema, types);
		} else if (pDefinition.has("allOf")) {
			result = resolveAllOf(name, pDefinition, jsonSchema);
		} else if (pDefinition.has("format")) {
			pDefinition.put("type", "string");
			result = resolveType(name, pDefinition, jsonSchema);
		} else if (pDefinition.has("enum")) {
			// log.info("-= RESOLVE ENUM " + pDefinition + " =-");
			JSONArray valArr = pDefinition.getJSONArray("enum");
			result = resolveEnum(valArr);
		} else if (pDefinition.has("properties")) {
			// JSONObject props = pDefinition.getJSONObject("properties");
			// result = generateObject(name, props, jsonSchema);
			Map<String, Object> p = new HashMap<>();

			for (String key : pDefinition.getJSONObject("properties").keySet()) {
				Object attr = this.generateObject(key, pDefinition.getJSONObject("properties").getJSONObject(key),
						jsonSchema);
				p.put(key, attr);
			}

			JSONObject o = new JSONObject(p);
			result = o;
		} else {
			log.error("!!! cannot resolve attribute '" + name + "' => " + pDefinition);
		}

		return result;
	}

	private Object resolveEnum(JSONArray vals) {
		List<Object> v = vals.toList();
		int i = dataFactory.getNumberBetween(0, v.size());
		return v.get(i);
	}

	private Object resolveAllOf(String name, JSONObject pDefinition, JSONObject jsonSchema) {
		// TODO max recursion loop, e. g. AAS 3.0
		
		Object result;
		JSONArray types = pDefinition.getJSONArray("allOf");

		List<Object> all = new ArrayList<>();
		JSONObject rs = new JSONObject();

		int count = 0;
		for (Object t : types.toList()) {
			Object o = generateObject(name, new JSONObject((Map) t), jsonSchema);

			if (o != null && o instanceof JSONObject) {
				JSONObject jo = (JSONObject) o;
				for (String key : jo.keySet()) {
					Object val = jo.get(key);
					if(val != null) {
						rs.put(key, val);
					}
				}
			}
		}

		result = rs;
		return result;
	}

	private Object resolveOption(String name, JSONObject jsonSchema, JSONArray types) {
		Object result = null;
		List<Object> tsl = types.toList();

		int i = dataFactory.getNumberBetween(0, tsl.size());

		Map defO = (Map) tsl.get(i);
		JSONObject def = new JSONObject(defO);

		result = this.generateObject(name, def, jsonSchema);
		// log.info(" -> Generate Option for " + name + " -> " + def + " := " + result);

		return result;
	}

	private Object resolveType(String name, JSONObject pDefinition, JSONObject jsonSchema) {
		Object result = null;
		Object t = pDefinition.get("type");

		if (t instanceof String) {
			String type = pDefinition.getString("type").toLowerCase();

			switch (type) {
			case "number":
				result = this.generateNumber(name, pDefinition);
				break;
			case "integer":
				result = this.generateInteger(name, pDefinition);
				break;
			case "string":
				result = this.generateString(name, pDefinition);
				break;
			case "array":
				JSONObject item = pDefinition.getJSONObject("items");
				result = this.generateArray(name, item, jsonSchema);
				break;
			case "boolean":
				result = (this.dataFactory.getNumberBetween(0, 2) > 0) ? Boolean.TRUE : Boolean.FALSE;
				break;
			case "object":
				// log.error("type 'object' not yet supported: " + pDefinition);
				if (pDefinition.has("properties")) {
					JSONObject properties = pDefinition.getJSONObject("properties");
					Map<String, Object> p = new HashMap<>();

					for (String key : properties.keySet()) {
						Object attr = this.generateObject(key, properties.getJSONObject(key), jsonSchema);
						p.put(key, attr);
					}

					result = new JSONObject(p);
				} else {
					// JSONObject copy = new JSONObject(pDefinition.toMap());
					// log.info("OIDAAAA!!!! " + pDefinition);
					// copy.remove("type");

					if (pDefinition.has("oneOf")) {
						JSONArray arr = pDefinition.getJSONArray("oneOf");
						result = resolveOption(name, jsonSchema, arr);
						// log.info(" Got: " + result + " for " + arr);
					} else {
						result = null;
					}
				}
				break;
			default:
				result = null;
				break;
			}
		} else {
			// log.info("Type not String: " + t + " -> " + t.getClass().getCanonicalName());
			JSONObject typeObject = (JSONObject) t;
			if (typeObject.has("enum")) {
				result = resolveEnum(typeObject.getJSONArray("enum"));
			} else {
				throw new RuntimeException("Unsupported type: " + t);
			}
		}
		return result;
	}

	private Object resolveRef(String name, JSONObject pDefinition, JSONObject jsonSchema, Object result) {
		String target = pDefinition.getString("$ref");
		JSONObject ref = this.resolveRef(target, jsonSchema);

		// log.info("Resolve $ref: " + name + " => " + ref);

		if (ref != null) {
			result = this.generateObject(name, ref, jsonSchema);
		}
		return result;
	}

	private JSONObject resolveRef(String target, JSONObject jsonSchema) {
		JSONObject result = null;

		// log.info(" ==> Resolve '" + target + "'");
		String[] path = target.split("/");

		if ("schemas".equals(path[1].toLowerCase())) {
			try {
				String schema = path[2];
				String version = path[3];

				// log.info("try to resolve schema reference '" + schema + " v" + version);

				// String fname = schema + "_v" + version + ".json";

				// throw new RuntimeException("Cannot resolve external schemas yet! Requried: "
				// + schema + " => " + fname);

				return this.metamodelRepository.getMetamodel(schema, version);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			JSONObject repo = jsonSchema;

			for (int i = 1; i < path.length; i++) {
				try {
					if (i < path.length) {
						repo = repo.getJSONObject(path[i]);
					} else {
						throw new RuntimeException("index out of bounds: " + i + " for " + target);
					}
				} catch (JSONException e) {
					log.error(e.getMessage(), e);
				}
			}

			result = repo;
		}
		return result;
	}

	private List<Object> generateArray(String name, JSONObject item, JSONObject jsonSchema) {
		List<Object> result = new ArrayList<>();

		int count = dataFactory.getNumberBetween(1, 5);
		for (int i = 0; i < count; i++) {
			result.add(this.generateObject(name, item, jsonSchema));
		}

		return result;
	}

	private Number generateNumber(String name, JSONObject d) {
		Number result = null;

		String n = null;
		if (name != null) {
			n = name.strip().toLowerCase();
		}

		BigDecimal multiplier = new BigDecimal(100);

		BigDecimal max = null;
		BigDecimal min = null;

		if (d.has("maximum")) {
			max = d.getBigDecimal("maximum");
		} else {
			max = new BigDecimal(65000);
		}

		if (d.has("minimum")) {
			min = d.getBigDecimal("minimum");
		} else {
			min = new BigDecimal(0);
		}

		try {
			result = rand(min, max);
		} catch (Exception e) {
			log.error("Error generating @" + name + ": " + e.getMessage());
			result = 0;
		}

		// log.info("@" + name + " := " + result + " (Min: " + min + " - Max: " + max + ")");
		return result;
	}

	private Number rand(BigDecimal min, BigDecimal max) throws Exception {
		Number result = null;

		BigDecimal range = max.subtract(min);
		BigDecimal r = min.add(range.multiply(new BigDecimal(Math.random())));
		
		result = r;

		return result;
	}

	private Integer generateInteger(String name, JSONObject d) {
		Integer result = null;

		String n = null;
		if (name != null) {
			n = name.strip().toLowerCase();
		}

		Integer max = null;
		Integer min = null;

		if (d.has("maximum")) {
			max = d.getInt("maximum");
		} else {
			max = 65000;

			if ("age".equals(n)) {
				max = 100;
			}
		}

		if (d.has("minimum")) {
			min = d.getInt("minimum");
		} else {
			min = 0;
		}

		result = dataFactory.getNumberBetween(min, max);

		return result;
	}

	private String generateString(String name, JSONObject d) {
		String result = null;

		if (d.has("format")) {
			String format = d.getString("format");
			// log.info(" => generate according to format: " + format);
			// TODO: format generation
			switch (format) {
			case "email":
				result = dataFactory.getEmailAddress();
				break;
			case "mail":
				result = dataFactory.getEmailAddress();
				break;
			case "date":

				break;
			case "time":

				break;
			case "date-time":

				break;
			case "uriref":
				result = "https://www.google.com";
				break;
			default:
				result = "";
				break;
			}
		} else if (d.has("pattern")) {
			String pattern = d.getString("pattern");
			// log.info(" => generate according to pattern: " + pattern);
			result = RandomString.fromRegex(pattern);
		} else {
			Integer max = null;
			Integer min = null;

			if (d.has("maximum")) {
				max = d.getInt("maximum");
			} else {
				max = 50;
			}

			if (d.has("minimum")) {
				min = d.getInt("minimum");
			} else {
				min = 0;
			}

			String n = null;
			if (name != null) {
				n = name.strip().toLowerCase();
			}

			switch (n) {
			case "firstname":
				result = dataFactory.getFirstName();
				break;
			case "lastname":
				result = dataFactory.getLastName();
				break;
			case "name":
				result = dataFactory.getFirstName() + " " + dataFactory.getLastName();
				break;
			case "mail":
				result = dataFactory.getEmailAddress();
				break;
			case "email":
				result = dataFactory.getEmailAddress();
				break;
			default:
				result = dataFactory.getRandomChars(min, max);
				break;
			}

			if (n != null) {
				if (n.startsWith("street")) {
					result = dataFactory.getStreetName();
				} else if (n.startsWith("city")) {
					result = dataFactory.getCity();
				} else if (n.startsWith("postal")) {
					result = dataFactory.getNumberText(5);
				} else if (n.contains("mail")) {
					result = dataFactory.getEmailAddress();
				}
			}
		}

		return result;
	}

}
