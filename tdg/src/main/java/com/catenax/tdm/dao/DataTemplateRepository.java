package com.catenax.tdm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.catenax.tdm.model.DataTemplate;
import com.catenax.tdm.model.TestDataScenario;

public interface DataTemplateRepository extends MongoRepository<DataTemplate, String> {
	
	public Optional<DataTemplate> findByNameAndVersion(String name, String version);
	
	public Optional<List<DataTemplate>> findAllByName(String name);
	
	public Optional<List<DataTemplate>> findAllByVersion(String version);

}
