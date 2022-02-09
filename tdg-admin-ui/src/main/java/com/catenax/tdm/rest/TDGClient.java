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
import com.google.gson.internal.LinkedTreeMap;

public class TDGClient {
	
	private ApiClient connection = null;
	
	private String baseUrl = "http://localhost:8080";
	
	
	
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
			// System.out.println(response.getClass().getCanonicalName());
			// System.out.println(response);
			List<Object> items = (List<Object>) response;
			for(Object o : items) {
				System.out.println(o.getClass().getCanonicalName());
				// System.out.println(o);
				LinkedTreeMap ltm = (LinkedTreeMap) o;
				
				TestDataScenario scenario = new TestDataScenario();
				
				scenario.setName(ltm.get("name").toString());
				scenario.setVersion(ltm.get("version").toString());
				scenario.setContent(ltm.get("content").toString());
				
				System.out.println("Found Scenario: " + scenario.getName());
				
				result.add(scenario);
				
				
				for(Object key : ltm.keySet()) {
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
			Object response =  this.getDataTemplateApi().getDataTemplatesUsingGET("*", "*");
			
			for(Object o : ((List<Object>) response)) {
				System.out.println(o.getClass().getCanonicalName());
				// System.out.println(o);
				LinkedTreeMap ltm = (LinkedTreeMap) o;
				
				DataTemplate templ = new DataTemplate();
				
				templ.setName(ltm.get("name").toString());
				templ.setVersion(ltm.get("version").toString());
				templ.setContent(ltm.get("content").toString());
				
				System.out.println("Found Template: " + templ.getName());
				
				result.add(templ);
				
				
				for(Object key : ltm.keySet()) {
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
