package com.catenax.tdm.metamodel;

import java.util.Optional;

import org.json.JSONObject;

import com.catenax.tdm.dao.MetaModelRepository;
import com.catenax.tdm.model.MetaModel;

public class CombinedMetamodelRepository implements MetaModelResourceRepository {
	// Class to get metamodel either from db or from resource (fallback)
	private MetaModelRepository mm1;
	private MetaModelResourceRepository mm2;
	
	public CombinedMetamodelRepository(MetaModelRepository dbMetaModelRepository, MetaModelResourceRepository metamodelRepository) {
		this.mm1 = dbMetaModelRepository;
		this.mm2 = metamodelRepository;
	}

	@Override
	public String getMetamodelAsString(String pMetamodel, String pVersion) throws Exception {
		String result = null;
		
		Optional<MetaModel> mmo = mm1.findByNameAndVersion(pMetamodel, pVersion);
		if(mmo.isPresent()) {
			result = mmo.get().getContent();
		} else {
			result = mm2.getMetamodelAsString(pMetamodel, pVersion);
		}
		
		return result;
	}

	@Override
	public JSONObject getMetamodel(String pMetamodel, String pVersion) throws Exception {
		return new JSONObject(getMetamodelAsString(pMetamodel, pVersion));
	}
	
}