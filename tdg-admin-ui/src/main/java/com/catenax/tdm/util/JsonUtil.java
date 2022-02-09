package com.catenax.tdm.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class JsonUtil {

	public static String fixContent(String content) {
		String result = content
				//.replaceAll("(\\r|\\n|\\r\\n)", "\n")
				.replaceAll("\\t", "    ")
				.replaceAll("\\\\n", "\n")
				.replaceAll("\\\\r", "")
				.replaceAll("\\\\u0027", "'")
				.replaceAll("\\\\u003d", "=")
				.replaceAll("\\\\\"", "\"")
		;
		
		if(result.startsWith("\"")) {
			result = result.substring(1);
		}
		
		if(result.endsWith("\"")) {
			result = result.substring(0, result.length() - 1);
		}
		
		return result;
	}
	
	public static String prettyPrint(String json) {
		String res = json;

		try {
			ObjectMapper om = new ObjectMapper();
			res = om.writerWithDefaultPrettyPrinter().writeValueAsString(om.readTree(json));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static String toJson(LinkedTreeMap<?,?> ltm) {
		Gson gson = new Gson();
		String js = gson.toJsonTree(ltm).getAsJsonObject().toString();
		return js;
	}
}
