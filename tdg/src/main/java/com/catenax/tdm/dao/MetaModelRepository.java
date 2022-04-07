package com.catenax.tdm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.catenax.tdm.model.DataTemplate;
import com.catenax.tdm.model.MetaModel;

public interface MetaModelRepository extends MongoRepository<MetaModel, String> {
	
	public Optional<MetaModel> findByNameAndVersion(String name, String version);
	
	public Optional<List<MetaModel>> findAllByName(String name);
	
	public Optional<List<MetaModel>> findAllByVersion(String version);

}
