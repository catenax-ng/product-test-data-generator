package com.catenax.tdm.rest;

import java.util.ArrayList;
import java.util.List;

import com.catenax.tdm.client.ApiClient;
import com.catenax.tdm.client.ApiException;
import com.catenax.tdm.client.api.DataTemplateApi;
import com.catenax.tdm.client.api.TestdataScenarioApi;
import com.catenax.tdm.client.api.TestdataScenarioInstanceApi;
import com.catenax.tdm.client.model.DataTemplate;
import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.client.model.TestDataScenario.ScriptTypeEnum;
import com.google.gson.internal.LinkedTreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TDGClient {

	private ApiClient connection = null;

	private String baseUrl = "http://localhost:8080";

	private static final Logger log = LoggerFactory.getLogger(TDGClient.class);

	public TDGClient() {
		this.connection = new ApiClient();
		// this.connection.setApiKey(API_KEY);
		// this.connection.setApiKeyPrefix("");
	}

	public ApiClient getApiClient() {
		return this.connection;
	}

	public TestdataScenarioApi getTestdataScenarioApi() {
		return new TestdataScenarioApi(this.getApiClient());
	}

	public TestdataScenarioInstanceApi getTestdataScenarioInstanceApi() {
		return new TestdataScenarioInstanceApi(this.getApiClient());
	}

	public DataTemplateApi getDataTemplateApi() {
		return new DataTemplateApi(this.getApiClient());
	}

	public List<TestDataScenario> getTestScenarioDefinitions() {
		List<TestDataScenario> result = new ArrayList<>();

		try {
			Object response = this.getTestdataScenarioApi().getTestdataScenarioUsingGET("*", "*");

			List<Object> items = (List<Object>) response;
			for (Object o : items) {
				LinkedTreeMap ltm = (LinkedTreeMap) o;

				TestDataScenario scenario = new TestDataScenario();

				scenario.setName(ltm.get("name").toString());
				scenario.setVersion(ltm.get("version").toString());
				scenario.setContent(ltm.get("content").toString());

				String scriptType = ltm.get("scriptType").toString();
				System.out.println("ScriptType: " + scriptType);

				if ("JavaScript".equals(scriptType)) {
					scenario.setScriptType(ScriptTypeEnum.JAVASCRIPT);
				} else {
					scenario.setScriptType(ScriptTypeEnum.DSL);
				}

				// System.out.println("Found Scenario: " + scenario.getName());

				result.add(scenario);

				for (Object key : ltm.keySet()) {
					Object value = ltm.get(key);
					// System.out.println(key + " := " + value);
					// result.add(ltm.get("name") + " (" + ltm.get("version") + ")");

				}
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<DataTemplate> getDataTemplates() {
		List<DataTemplate> result = new ArrayList<>();

		try {
			Object response = this.getDataTemplateApi().getDataTemplatesUsingGET("*", "*");

			for (Object o : ((List<Object>) response)) {
				// System.out.println(o.getClass().getCanonicalName());
				// System.out.println(o);
				LinkedTreeMap ltm = (LinkedTreeMap) o;

				DataTemplate templ = new DataTemplate();

				templ.setName(ltm.get("name").toString());
				templ.setVersion(ltm.get("version").toString());
				templ.setContent(ltm.get("content").toString());

				// System.out.println("Found Template: " + templ.getName());

				result.add(templ);

				for (Object key : ltm.keySet()) {
					Object value = ltm.get(key);
					// System.out.println(key + " := " + value);
					// result.add(ltm.get("name") + " (" + ltm.get("version") + ")");

				}
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return result;
	}

}
