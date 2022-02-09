package com.catenax.tdm.scripting;

import javax.script.Bindings;
import javax.script.ScriptEngineManager;

import org.fluttercode.datafactory.impl.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.dao.DataTemplateRepository;
import com.catenax.tdm.metamodel.MetamodelRepository;
import com.catenax.tdm.model.TestDataScenario;
import com.catenax.tdm.resource.TDMResourceLoader;
import com.catenax.tdm.scenario.TestDataScenarioFactory;
import com.catenax.tdm.testdata.TestDataFactory;
import com.catenax.tdm.testdata.TestDataGenerator;

public class ScriptEngine {

	private static final Logger log = LoggerFactory.getLogger(ScriptEngine.class);
	
	private static final TestDataFactory dataFactory = new TestDataFactory();

	private javax.script.ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

	private MetamodelRepository metamodelRepository;
	private TestDataGenerator testdataGenerator;
	private TestDataScenarioFactory scenarioFactory;
	private DataTemplateRepository dataTemplateRepository;

	public ScriptEngine(MetamodelRepository metamodelRepository, TestDataGenerator testdataGenerator) {
		this.metamodelRepository = metamodelRepository;
		this.testdataGenerator = testdataGenerator;

		this.scenarioFactory = new TestDataScenarioFactory(metamodelRepository, testdataGenerator);
	}

	private Bindings createBindings() {
		Bindings b = this.engine.createBindings();

		b.put("log", log);
		b.put("metamodel", this.metamodelRepository);
		b.put("generator", this.testdataGenerator);
		b.put("scenario", this.scenarioFactory);		
		b.put("dataFactory", dataFactory);
		b.put("rand", dataFactory);

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
			Bindings bindings = this.createBindings();

			content = TDMResourceLoader.resourceToString("TestDataScenarioScriptTemplate.js").replace("${script}",
					script);

			log.info("Execute Script: " + content);

			this.scenarioFactory.flush();
			engine.eval(content, bindings);
			return this.scenarioFactory.getResult(includeGraphQL);
		} catch (Exception e) {
			log.error(content);
			e.printStackTrace();
			throw e;
		} 
	}

	public void setDataTemplateRepository(DataTemplateRepository dataTemplateRepository) {
		this.dataTemplateRepository = dataTemplateRepository;
		this.scenarioFactory.setDataTemplateRepository(dataTemplateRepository);
	}

}
