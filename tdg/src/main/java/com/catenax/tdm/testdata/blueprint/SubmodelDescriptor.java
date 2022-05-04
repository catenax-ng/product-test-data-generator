package com.catenax.tdm.testdata.blueprint;

import java.util.ArrayList;
import java.util.List;
import com.catenax.tdm.util.DigestUtils;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmodelDescriptor {
	
	public static SubmodelDescriptor create(GenerationItem gi, JSONObject item) {
		SubmodelDescriptor result = new SubmodelDescriptor();
		
		String modelName = gi.getModelName();
		String idShort = "";
		
		char[] mn = modelName.toCharArray();
		for(int i = 0; i < mn.length; i++) {
			char c = mn[i];
			if((""+c).equals((""+c).toUpperCase())) {
				if(i==0) {
					idShort += (""+c).toLowerCase();
				} else {
					idShort += "-" + (""+c).toLowerCase();
				}
			} else {
				idShort += c;
			}
		}
		
		String semantic = idShort.replaceAll("-", "_");

		String ident = modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
		
		result.setIdentification(ident);
	    result.setIdShort(idShort);
		
	    String sidStr = "urn:bamm:com.catenax." + semantic + ":1.0.0";
		Value sid = result.new Value(sidStr);
	    result.getSemanticId().add(sid);
	    
	    ProtocolInformation pi = result.new ProtocolInformation();
	    
	    // Old Connector mock url
	    // String edc = "edc://offer-trace-" + idShort + "/shells/" + item.getString("catenaXId") + "/aas/" + idShort;
	    
	    // Version improved on 09.04.2022
	    //String bpn = item.getJSONArray("localIdentifiers").getJSONObject(0).getString("value");
	    //String model = sidStr;
	    //String edc = "http://provider.connector:port/" + bpn + "/" + model + "/submodel?content=value&extent=WithBLOBValue";

		// Version improvement on 03.05.2022
		String bpn = item.getJSONArray("localIdentifiers").getJSONObject(0).getString("value");
		String model = sidStr;
		String AASID = "test";
		String edc="http://provider.connector:port/" + bpn + "/" + AASID + "-" + DigestUtils.uuidFromHash(sidStr) + "/submodel?content=value&extent=WithBLOBValue";


		pi.setEndpointAddress(edc);
	    pi.setEndpointProtocol("AAS/SUBMODEL");
	    pi.setEndpointProtocolVersion("1.0RC02");
	    
	    String url = "https://" + bpn + ".connector"; 
	    Endpoint ep = result.new Endpoint(url, pi);
	    result.getEndpoints().add(ep);

		return result;
	}

	public class Value {
		@JsonProperty("value")
		private String value = null;
		
		public Value() {
			
		}
		
		public Value(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
	
	public class ProtocolInformation {
		
		@JsonProperty("endpointAddress")
		private String endpointAddress = null;
		
		@JsonProperty("endpointProtocol")
		private String endpointProtocol = null;
		
		@JsonProperty("endpointProtocolVersion")
		private String endpointProtocolVersion = null;
		
		public ProtocolInformation() {
			
		}

		public String getEndpointAddress() {
			return endpointAddress;
		}

		public void setEndpointAddress(String endpointAddress) {
			this.endpointAddress = endpointAddress;
		}

		public String getEndpointProtocol() {
			return endpointProtocol;
		}

		public void setEndpointProtocol(String endpointProtocol) {
			this.endpointProtocol = endpointProtocol;
		}

		public String getEndpointProtocolVersion() {
			return endpointProtocolVersion;
		}

		public void setEndpointProtocolVersion(String endpointProtocolVersion) {
			this.endpointProtocolVersion = endpointProtocolVersion;
		}
	}
	
	public class Endpoint {
		
		@JsonProperty("interface")
		private String interfaceName = null;
		
		@JsonProperty("protocolInformation")
		private ProtocolInformation protocolInformation = null;
		
		public Endpoint() {
			
		}
		
		public Endpoint(String ifName, ProtocolInformation pInfo) {
			this.interfaceName = ifName;
			this.protocolInformation = pInfo;
		}

		public String getInterfaceName() {
			return interfaceName;
		}

		public void setInterfaceName(String interfaceName) {
			this.interfaceName = interfaceName;
		}

		public ProtocolInformation getProtocolInformation() {
			return protocolInformation;
		}

		public void setProtocolInformation(ProtocolInformation protocolInformation) {
			this.protocolInformation = protocolInformation;
		}
		
	}
	
	@JsonProperty("identification")
	private String identification = null;
	
	@JsonProperty("idShort")
	private String idShort = null;
	
	@JsonProperty("semanticId")
	private List<Value> semanticId = new ArrayList<>();
	
	
	@JsonProperty("endpoints")
	private List<Endpoint> endpoints = new ArrayList<>();
	
	public SubmodelDescriptor() {
		
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getIdShort() {
		return idShort;
	}

	public void setIdShort(String idShort) {
		this.idShort = idShort;
	}

	public List<Value> getSemanticId() {
		return semanticId;
	}

	public void setSemanticId(List<Value> semanticId) {
		this.semanticId = semanticId;
	}

	public List<Endpoint> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

}
