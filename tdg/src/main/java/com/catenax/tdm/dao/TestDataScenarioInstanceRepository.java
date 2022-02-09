package com.catenax.tdm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.catenax.tdm.model.TestDataScenarioInstance;

public interface TestDataScenarioInstanceRepository extends MongoRepository<TestDataScenarioInstance, String> {
	
	public Optional<TestDataScenarioInstance> findByNameAndScenarioId(String name, String scenarioId);
	
	public Optional<List<TestDataScenarioInstance>> findAllByScenarioId(String scenarioId);
	

}
