package com.catenax.tdm;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.catenax.tdm.metamodel.MetamodelRepository;
import com.catenax.tdm.metamodel.SemanticMetamodelRepository;
import com.catenax.tdm.scripting.ScriptEngine;
import com.catenax.tdm.testdata.SimpleTestDataGenerator;
import com.catenax.tdm.testdata.TestDataGenerator;

@Configuration
@EnableCaching
public class Config {

	public static String BASE_URL = "http://catena-x.net/tdg";
	public static Boolean VALIDATE = true;
	public static Boolean VALIDATE_EXIT = false;
	
	@Bean
	public MetamodelRepository metamodelRepository() {
		MetamodelRepository mr = new SemanticMetamodelRepository();
		
		return mr;
	}
	
	@Bean
	public TestDataGenerator testdataGenerator() {
		SimpleTestDataGenerator tdg = new SimpleTestDataGenerator();
		
		return tdg;
	}
	
	@Bean
	public ScriptEngine scriptEngine() {
		ScriptEngine engine = new ScriptEngine(metamodelRepository(), testdataGenerator());
		
		return engine;
	}

}
