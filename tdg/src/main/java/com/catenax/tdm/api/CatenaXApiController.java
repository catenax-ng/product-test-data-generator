package com.catenax.tdm.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.catenax.tdm.Config;
import com.catenax.tdm.dao.DataTemplateRepository;
import com.catenax.tdm.dao.TestDataScenarioInstanceRepository;
import com.catenax.tdm.dao.TestDataScenarioRepository;
import com.catenax.tdm.deo.TestDataScenarioInstanceStatus;
import com.catenax.tdm.metamodel.MetamodelRepository;
import com.catenax.tdm.model.DataTemplate;
import com.catenax.tdm.model.TestDataScenario;
import com.catenax.tdm.model.TestDataScenario.TestDataScenarioStatus;
import com.catenax.tdm.model.TestDataScenario.TestDataScenarioType;
import com.catenax.tdm.model.TestDataScenarioInstance;
import com.catenax.tdm.resource.TDMResourceLoader;
import com.catenax.tdm.scenario.TestDataScenarioExecutor;
import com.catenax.tdm.scenario.TestDataScenarioService;
import com.catenax.tdm.scripting.ScriptEngine;
import com.catenax.tdm.testdata.TestDataGenerator;
import com.catenax.tdm.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CatenaXApiController implements CatenaXApi {

	private static final Logger log = LoggerFactory.getLogger(CatenaXApiController.class);
	
	private static String API_KEY = null;

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	private MetamodelRepository metamodelRepository;

	@Autowired
	private TestDataGenerator testdataGenerator;

	@Autowired
	private ScriptEngine scriptEngine;

	@Autowired
	private TestDataScenarioRepository testDataScenarioRepository;

	@Autowired
	private TestDataScenarioInstanceRepository testDataScenarioInstanceRepository;

	@Autowired
	private DataTemplateRepository dataTemplateRepository;

	@org.springframework.beans.factory.annotation.Autowired
	public CatenaXApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
		
		// this.request.se
	}

	@PostConstruct
	private void init() {
		log.info("-= Post initialize ApiController ...");
		this.scriptEngine.setDataTemplateRepository(this.dataTemplateRepository);
		log.info("-= done =-");
	}
	
	private String _jsonToString(Object o) {
		String result = o.toString();

		if(o instanceof JSONObject) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setConfig(mapper.getSerializationConfig()
			   .with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
			);
			
			try {
				result = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(o);
			} catch (Exception e) {
				// log.error(e.getMessage());
			}
		}
		
		return result;
	}
	
	/*
	private boolean hasRole(String ... authroles) {
		boolean result = false;
		
		List<String> roles = new ArrayList<>();
		for(String ar : authroles) {
			roles.add(("role_" + ar).toUpperCase());
		}
		
		if(SecurityUtil.isUserLoggedIn()) {
			Authentication auth = SecurityUtil.getUser();
			// String username = auth.getName() + ", Details: " + auth.getDetails().getClass().getName() + ", Credentials: " + auth.getCredentials();
			// log.info("User is logged in as: " + username);
			for(GrantedAuthority a : auth.getAuthorities()) {
				String aa = a.getAuthority().toUpperCase();
				// log.info("  Authority: " + aa);
				if(roles.contains(aa)) {
					result = true;
				}
			}
		} else {
			log.info("User is not logged in");
			throw new RuntimeException("User is not logged in");
		}
		
		return result;
	}
	*/
	
	private void isAdmin() {
		/*
		if(!hasRole("tdg_admin")) {
			throw new RuntimeException("User is not logged in with admin rights");
		} else {
			log.info("User is logged in as admin");
		}
		*/
	}
	
	
	
	private void isUser() {
		/*
		if(SecurityUtil.isUserLoggedIn()) {
			Authentication auth = SecurityUtil.getUser();
			// String username = auth.getName() + ", Details: " + auth.getDetails().getClass().getName() + ", Credentials: " + auth.getCredentials();
			// log.info("User is logged in as: " + username);
			for(GrantedAuthority a : auth.getAuthorities()) {
				// log.info("  Authority: " + a.getAuthority());
			}
		} else {
			log.info("User is not logged in");
			throw new RuntimeException("User is not logged in");
		}
		*/
	}
	

	@Override
	public ResponseEntity<String> getModelDescription(String model, String version) {
		try {
			isUser();
			String result = this.metamodelRepository.getMetamodelAsString(model, version);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> getTestdata(String pModel, String pVersion, Integer pCount) {
		try {
			isUser();
			List<JSONObject> instances = new ArrayList<>();

			String json = getModelDescription(pModel, pVersion).getBody();

			JSONObject jsonSchema = new JSONObject(json);
			Schema schema = null;

			try {
				SchemaLoader loader = SchemaLoader.builder().schemaJson(jsonSchema).build();

				schema = loader.load().build();
			} catch (Exception e) {
				log.error(
						"Malformed or incomplete schema '" + pModel + "' version " + pVersion + ": " + e.getMessage());
			}

			JSONObject properties = jsonSchema.getJSONObject("properties");
			// log.info("properties:" + properties);
			for (int i = 0; i < pCount; i++) {

				Map<String, Object> p = new HashMap<>();

				for (String key : properties.keySet()) {
					Object attr = this.testdataGenerator.generateObject(key, properties.getJSONObject(key), jsonSchema);
					p.put(key, attr);
				}

				JSONObject o = new JSONObject(p);

				if (schema != null) {
					try {
						if (Config.VALIDATE) {
							schema.validate(o);
							log.info("schema validation ok for : " + o);
						}
					} catch (Exception e) {
						log.error("cannot validate result: " + e.getMessage());
					}
				} else {
					log.error("no valid schema found: " + jsonSchema);
				}

				instances.add(o);
			}

			JSONArray a = new JSONArray(instances);
			String result = this._jsonToString(a);

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<TestDataScenario>> getTestdataScenario(String scenario, String version) {
		isUser();
		List<TestDataScenario> result = new ArrayList<>();
		String wildcard = "*";
		if ((wildcard.equals(scenario) && wildcard.equals(version)) || (scenario == null && version == null)) {
			result = this.testDataScenarioRepository.findAll();
		} else if (wildcard.equals(scenario) && version != null) {
			Optional<List<TestDataScenario>> o = this.testDataScenarioRepository.findAllByVersion(version);
			if (o.isPresent()) {
				result = o.get();
			}
		} else if (scenario != null && wildcard.equals(version)) {
			Optional<List<TestDataScenario>> o = this.testDataScenarioRepository.findAllByName(scenario);
			if (o.isPresent()) {
				result = o.get();
			}
		} else {
			Optional<TestDataScenario> tds = this.testDataScenarioRepository.findByNameAndVersion(scenario, version);
			if (tds.isPresent()) {
				result.add(tds.get());
			} else {
				// log.error("cannot find TestDataScenario with name '" + scenario + "' version " + version);
				String fname = "scenario/" + scenario + "_v" + version + ".txt";
				try {
					log.info("loading scenario from resource template");
					String str = TDMResourceLoader.resourceToString(fname);
					
					TestDataScenario tdst = new TestDataScenario(); // this.objectMapper.readValue(str, TestDataScenario.class);
					tdst.setName(scenario);
					tdst.setVersion(version);
					tdst.setScriptStatus(TestDataScenarioStatus.PRODUCTIVE);
					tdst.setContent(str);
					
					result.add(tdst);					
				} catch (IOException e) {
					log.error("cannot find TestDataScenario with name '" + scenario + "' version " + version);
					return new ResponseEntity<List<TestDataScenario>>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		return new ResponseEntity<List<TestDataScenario>>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TestDataScenario> createTestdataScenario(TestDataScenario data) {
		isUser();
		TestDataScenario result = this.testDataScenarioRepository.insert(data);
		return new ResponseEntity<TestDataScenario>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TestDataScenario> updateTestdataScenario(TestDataScenario data) {
		isUser();
		
		
		String content = data.getContent();
		content = JsonUtil.fixContent(content);
		data.setContent(content);
		
		log.info("Save:");
		log.info(content);
		
		TestDataScenario result = this.testDataScenarioRepository.save(data);
		return new ResponseEntity<TestDataScenario>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TestDataScenario> updateTestdataScenarioContent(String scenario, String version,
			TestDataScenarioType scriptType, TestDataScenarioStatus scriptStatus, @Valid String content) {
		isUser();
		TestDataScenario result = null;

		try {
			String id = scenario + "_" + version;
			Optional<TestDataScenario> tds = this.testDataScenarioRepository.findByNameAndVersion(scenario, version);
			if (tds.isPresent()) {
				result = tds.get();
				
				String c = JsonUtil.fixContent(content);
				log.info("Save:");
				log.info(content);

				result.setScriptStatus(scriptStatus);
				result.setScriptType(scriptType);
				result.setContent(c);

				this.testDataScenarioRepository.save(result);
			} else {
				log.error("cannot find TestDataScenario with name '" + scenario + "' version " + version);
				return new ResponseEntity<TestDataScenario>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<TestDataScenario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<TestDataScenario>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TestDataScenario> deleteTestdataScenario(TestDataScenario data) {
		try {
			isUser();
			TestDataScenario result = this.getTestdataScenario(data.getName(), data.getVersion()).getBody().get(0);
			
			log.info("DELETE: " + result.toString());
			
			this.testDataScenarioRepository.delete(result);
			return new ResponseEntity<TestDataScenario>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<TestDataScenario>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private String saveTestdataScenarioInstance(TestDataScenario scenario, String name, String value) {
		String result = null;
		isUser();

		Optional<TestDataScenarioInstance> tdsi = this.testDataScenarioInstanceRepository.findByNameAndScenarioId(name,
				scenario.get_id());
		if (tdsi.isPresent()) {
			result = tdsi.get().getContent();
		} else {
			TestDataScenarioInstance i = new TestDataScenarioInstance();

			i.setScenarioId(scenario.get_id());
			i.setName(name);
			i.setContent(value);

			this.testDataScenarioInstanceRepository.insert(i);

			result = value;
		}
		return result;
	}

	@Override
	public ResponseEntity<String> instantiateTestdataScenario(String scenario, String version, String name, boolean overwrite, boolean includeGraphQL) {
		try {
			isUser();
			String result = null;

			List<TestDataScenario> scs = this.getTestdataScenario(scenario, version).getBody();
			
			if(scs == null || scs.size() != 1) {
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			Optional<TestDataScenario> tdso = Optional.of(scs.get(0)); // this.testDataScenarioRepository.findByNameAndVersion(scenario, version);
			
			if (tdso.isPresent()) {
				TestDataScenario tds = tdso.get();

				if (tds.getScriptStatus() == TestDataScenarioStatus.PRODUCTIVE) {
					String script = tds.getContent();
					
					if(overwrite) {
						log.info("DELETE TestDataScenario first: " + scenario + "-" + version + "-" + name);
						this.deleteTestdataScenarioInstance(scenario, version, name);
					}
					
					if (tds.getScriptType() == TestDataScenarioType.DSL) {
						TestDataScenarioExecutor executor = TestDataScenarioService
								// .parseScenarioFromResource("scenario/" + scenario + "_v" + version + ".txt");
								.parseScenarioFromString(tds.getContent());

						executor.setMetamodelRepository(this.metamodelRepository);
						executor.setTestdataGenerator(testdataGenerator);

						result = executor.execute(this.scriptEngine, includeGraphQL);
						result = this.saveTestdataScenarioInstance(tds, name, result);
						return new ResponseEntity<String>(result, HttpStatus.OK);
					} else if (tds.getScriptType() == TestDataScenarioType.JavaScript) {
						result = this.scriptEngine.executeScript(script, includeGraphQL).toString();
						result = this.saveTestdataScenarioInstance(tds, name, result);
						return new ResponseEntity<String>(result, HttpStatus.OK);
					}
				} else {
					String err = "Please set your script to status 'PRODUCTIVE' before you generate instances. Current status: '"
							+ tds.getScriptStatus() + "'.";
					throw new RuntimeException(err);
				}
			} else {
				log.error("cannot find TestDataScenario with name '" + scenario + "' version " + version);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
			// throw e;
			return new ResponseEntity<String>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private boolean use_base64 = true;

	@Override
	public ResponseEntity<String> instantiateTestdataScenarioRaw(TestDataScenarioType scriptType, String script) {
		String result = "";
		
		try {
			
			String code = script;
			if(use_base64) {
				Decoder decoder = Base64.getDecoder();
				
				int start = 0; // 1;
				if(script.startsWith("\"")) {
					start = 1;
				}
				int sub = 0; // script.length() - 13
				
				String s = script.replaceAll("\"", "").replaceAll("u003d", "=").replaceAll("\\\\", "");
				
				String x = s; //script.substring(start, script.length() - sub) + "==";
				// log.info("orig: " + script);
				log.info("x := '" + x + "'");
				code = new String(decoder.decode(x.getBytes()));
				
				code= JsonUtil.fixContent(code);
			}
			
			log.info(code);
			
			TestDataScenario tds = new TestDataScenario();
			tds.setName("unnamed");
			tds.setVersion("0");
			tds.setScriptStatus(TestDataScenarioStatus.PRODUCTIVE);
			tds.setScriptType(scriptType);
			tds.setContent(code);
			
			log.info("EXECUTE: " + code);
			/*
			TestDataScenarioExecutor executor = TestDataScenarioService
					.parseScenarioFromString(code);
			
			executor.setMetamodelRepository(this.metamodelRepository);
			executor.setTestdataGenerator(testdataGenerator);

			result = executor.execute(this.scriptEngine, false);

			// log.info("RESULT: " + result);
			
			return new ResponseEntity<String>(result, HttpStatus.OK);
			*/
			if (tds.getScriptType() == TestDataScenarioType.DSL) {
				TestDataScenarioExecutor executor = TestDataScenarioService
						// .parseScenarioFromResource("scenario/" + scenario + "_v" + version + ".txt");
						.parseScenarioFromString(tds.getContent());

				executor.setMetamodelRepository(this.metamodelRepository);
				executor.setTestdataGenerator(testdataGenerator);

				result = executor.execute(this.scriptEngine, false);
				// result = this.saveTestdataScenarioInstance(tds, name, result);
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else if (tds.getScriptType() == TestDataScenarioType.JavaScript) {
				result = this._jsonToString(this.scriptEngine.executeScript(code, false));
				// result = this.saveTestdataScenarioInstance(tds, name, result);
				
				// log.info("Result: " + result);
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		} catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("ERROR: unknown script", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Boolean> deleteTestdataScenarioInstance(String scenario, String version, String name) {
		try {
			isUser();

			// Optional<TestDataScenario> tds = this.testDataScenarioRepository.findByNameAndVersion(scenario, version);
			
			List<TestDataScenario> scs = this.getTestdataScenario(scenario, version).getBody();
			
			if(scs == null || scs.size() != 1) {
				return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			Optional<TestDataScenario> tds = Optional.of(scs.get(0)); // this.testDataScenarioRepository.findByNameAndVersion(scenario, version);
			
			if (tds.isPresent()) {
				Optional<TestDataScenarioInstance> tdsi = this.testDataScenarioInstanceRepository
						.findByNameAndScenarioId(name, tds.get().get_id());
				if (tdsi.isPresent()) {
					this.testDataScenarioInstanceRepository.delete(tdsi.get());
					return new ResponseEntity<Boolean>(true, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<TestDataScenarioInstanceStatus>> listTestdataScenarioInstances(String scenario,
			String version, String name) {
		try {
			isUser();
			String wildcard = "*";

			List<TestDataScenarioInstanceStatus> result = new ArrayList<>();

			List<TestDataScenario> scenarios = this.getTestdataScenario(scenario, version).getBody();

			for (TestDataScenario s : scenarios) {
				List<TestDataScenarioInstance> instances = new ArrayList<>();

				for (TestDataScenarioInstance i : this.testDataScenarioInstanceRepository
						.findAllByScenarioId(s.get_id()).get()) {
					if (wildcard.equals(name) || name == null || name.equals(i.getName())) {
						instances.add(i);
					}
				}

				for (TestDataScenarioInstance i : instances) {
					result.add(TestDataScenarioInstanceStatus.fromInstance(s, i));
				}
			}
			return new ResponseEntity<List<TestDataScenarioInstanceStatus>>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());

		}
		return new ResponseEntity<List<TestDataScenarioInstanceStatus>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> queryTestdataScenarioInstances(String scenario,
			String version, String name, String query) {
		String result = null;
		
		try {
			isUser();
			Optional<TestDataScenario> tds = this.testDataScenarioRepository.findByNameAndVersion(scenario, version);
			if (tds.isPresent()) {
				Optional<TestDataScenarioInstance> tdsi = this.testDataScenarioInstanceRepository
						.findByNameAndScenarioId(name, tds.get().get_id());
				if (tdsi.isPresent()) {
					String content = tdsi.get().getContent();
					JSONObject json = new JSONObject(content);
					
					
				}
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		
		if(result == null) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Boolean> setTestdataScenarioStatus(String scenario, String version,
			TestDataScenarioStatus status) {
		try {
			isUser();
			Optional<TestDataScenario> tds = this.testDataScenarioRepository.findByNameAndVersion(scenario, version);
			if (tds.isPresent()) {
				tds.get().setScriptStatus(status);
				this.testDataScenarioRepository.save(tds.get());
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/* Data Template */

	@Override
	public ResponseEntity<List<DataTemplate>> getDataTemplates(String template, String version) {
		try {
			isUser();
			List<DataTemplate> result = new ArrayList<>();

			String wildcard = "*";
			if ((wildcard.equals(template) && wildcard.equals(version)) || (template == null && version == null)) {
				result = this.dataTemplateRepository.findAll();
			} else if (wildcard.equals(template) && version != null) {
				Optional<List<DataTemplate>> o = this.dataTemplateRepository.findAllByVersion(version);
				if (o.isPresent()) {
					result = o.get();
				}
			} else if (template != null && wildcard.equals(version)) {
				Optional<List<DataTemplate>> o = this.dataTemplateRepository.findAllByName(template);
				if (o.isPresent()) {
					result = o.get();
				}
			} else {
				Optional<DataTemplate> tds = this.dataTemplateRepository.findByNameAndVersion(template, version);
				if (tds.isPresent()) {
					result.add(tds.get());
				} else {
					log.error("cannot find DataTemplate with name '" + template + "' version " + version);
				}
			}

			return new ResponseEntity<List<DataTemplate>>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<List<DataTemplate>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<DataTemplate> createDataTemplate(@Valid DataTemplate dataTemplate) {
		try {
			isUser();
			Optional<DataTemplate> o = this.dataTemplateRepository.findByNameAndVersion(dataTemplate.getName(),
					dataTemplate.getVersion());
			if (o.isEmpty()) {
				this.dataTemplateRepository.insert(dataTemplate);
				DataTemplate result = dataTemplate;
				return new ResponseEntity<DataTemplate>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<DataTemplate>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<DataTemplate> updateDataTemplate(@Valid DataTemplate dataTemplate) {
		try {
			isUser();
			Optional<DataTemplate> o = this.dataTemplateRepository.findByNameAndVersion(dataTemplate.getName(),
					dataTemplate.getVersion());
			if (o.isPresent()) {
				DataTemplate result = o.get();

				result.setContent(dataTemplate.getContent());

				this.dataTemplateRepository.save(result);
				return new ResponseEntity<DataTemplate>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<DataTemplate>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Boolean> deleteDataTemplate(String template, String version) {
		try {
			isUser();
			Optional<DataTemplate> o = this.dataTemplateRepository.findByNameAndVersion(template, version);
			if (o.isPresent()) {
				this.dataTemplateRepository.delete(o.get());
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> test() {
		isUser();
		String result = null;
		try {
			
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DataTemplate> updateDataTemplateContent(String template, String version, String content) {
		try {
			isUser();
			Optional<DataTemplate> o = this.dataTemplateRepository.findByNameAndVersion(template, version);
			if (o.isPresent()) {
				DataTemplate result = o.get();

				result.setContent(JsonUtil.fixContent(content));

				this.dataTemplateRepository.save(result);
				return new ResponseEntity<DataTemplate>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return new ResponseEntity<DataTemplate>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
