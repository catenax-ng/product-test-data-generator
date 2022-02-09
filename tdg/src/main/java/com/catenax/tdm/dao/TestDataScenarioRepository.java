package com.catenax.tdm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.catenax.tdm.model.TestDataScenario;

public interface TestDataScenarioRepository extends MongoRepository<TestDataScenario, String> {
	
	public Optional<TestDataScenario> findByNameAndVersion(String name, String version);
	
	public Optional<List<TestDataScenario>> findAllByName(String name);
	
	public Optional<List<TestDataScenario>> findAllByVersion(String version);

}
