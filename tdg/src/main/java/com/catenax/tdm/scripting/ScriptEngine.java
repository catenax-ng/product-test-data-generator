package com.catenax.tdm.scripting;

import javax.script.Bindings;
import javax.script.ScriptEngineManager;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.dao.DataTemplateRepository;
import com.catenax.tdm.metamodel.MetaModelResourceRepository;
import com.catenax.tdm.model.TestDataScenario;
import com.catenax.tdm.resource.TDMResourceLoader;
import com.catenax.tdm.scenario.TestDataScenarioFactory;
import com.catenax.tdm.testdata.TestDataFactory;
import com.catenax.tdm.testdata.TestDataGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScriptEngine {

	private static final Logger log = LoggerFactory.getLogger(ScriptEngine.class);
	
	private static final TestDataFactory dataFactory = new TestDataFactory();

	private static final ScriptEngineManager ENGINE_MANAGER = getEngineManager();
	private javax.script.ScriptEngine engine = null;

	private MetaModelResourceRepository metamodelRepository;
	private TestDataGenerator testdataGenerator;
	// private TestDataScenarioFactory scenarioFactory;
	private DataTemplateRepository dataTemplateRepository;
	
	private static final ScriptEngineManager getEngineManager() {
		ScriptEngineManager result = new ScriptEngineManager();
		
		return result;
	}
	
	private static final javax.script.ScriptEngine getScriptEngine() {
		javax.script.ScriptEngine result = ENGINE_MANAGER.getEngineByName("nashorn");
		
		return result;
	}

	public ScriptEngine(MetaModelResourceRepository metamodelRepository, TestDataGenerator testdataGenerator) {
		this.metamodelRepository = metamodelRepository;
		this.testdataGenerator = testdataGenerator;

		// this.scenarioFactory = new TestDataScenarioFactory(metamodelRepository, testdataGenerator);
	}

	private Bindings createBindings() {
		this.engine = getScriptEngine();
		
		Bindings b = this.engine.createBindings();

		b.put("log", log);
		b.put("metamodel", this.metamodelRepository);
		b.put("generator", this.testdataGenerator);		
		b.put("dataFactory", dataFactory);
		b.put("rand", dataFactory);
		
		
		ObjectMapper om = new ObjectMapper();
		b.put("om", om);


		return b;
	}

	public Object executeScript(TestDataScenario script, boolean includeGraphQL) throws Exception {
		if (script != null) {
			return executeScript(script.getContent(), includeGraphQL);
		} else {
			return null;
		}
	}

	public Object executeScript(String script, boolean includeGraphQL) throws Exception {
		String content = null;
		try {
			TestDataScenarioFactory scenarioFactory = new TestDataScenarioFactory(this.metamodelRepository, this.testdataGenerator);
			scenarioFactory.setDataTemplateRepository(this.dataTemplateRepository);
			
			Bindings bindings = this.createBindings();
			bindings.put("scenario", scenarioFactory);

			content = TDMResourceLoader.resourceToString("TestDataScenarioScriptTemplate.js").replace("${script}",
					script);

			log.info("Execute Script: " + content);

			scenarioFactory.flush();
			engine.eval(content, bindings);
			return scenarioFactory.getResult(includeGraphQL);
		} catch (Exception e) {
			log.error(content);
			e.printStackTrace();
			throw e;
		} 
	}

	public void setDataTemplateRepository(DataTemplateRepository dataTemplateRepository) {
		this.dataTemplateRepository = dataTemplateRepository;
		// this.scenarioFactory.setDataTemplateRepository(dataTemplateRepository);
	}

}
