package com.catenax.tdm.testdata;

import org.json.JSONObject;

public interface TestDataGenerator {
	
	public Object generateObject(String name, JSONObject definition, JSONObject jsonSchema);

}
