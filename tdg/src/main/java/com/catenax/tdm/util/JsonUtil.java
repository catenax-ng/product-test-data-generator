package com.catenax.tdm.util;

public class JsonUtil {
	
	public static String fixContent(String content) {
		String result = content
				//.replaceAll("(\\r|\\n|\\r\\n)", "\n")
				.replaceAll("\\t", "    ")
				.replaceAll("\\\\n", "\n")
				.replaceAll("\\\\r", "")
				.replaceAll("\\\\u0027", "'")
				.replaceAll("\\\\u003d", "=")
				.replaceAll("\\\\u003c", "<")
				.replaceAll("\\\\u003e", ">")
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

}
