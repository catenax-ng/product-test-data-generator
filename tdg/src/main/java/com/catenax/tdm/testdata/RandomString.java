package com.catenax.tdm.testdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.curiousoddman.rgxgen.RgxGen;

public class RandomString {

	private static final Logger log = LoggerFactory.getLogger(RandomString.class);

	public static String fromRegex(String regex) {
		String result = "";
		String pattern = regex;

		if (regex.startsWith("?")) {
			pattern = regex.substring(1, regex.length());
		}

		try {
			// log.info("-=== String pattern: '" + pattern + "' ('" + regex + "')");
			RgxGen rgxGen = new RgxGen(pattern); // Create generator
			result = rgxGen.generate();
		} catch (Exception e) {
			log.error(e.getMessage() + " from pattern '" + pattern + "' ('" + regex + "')", e);
			// e.printStackTrace();
		}

		return result;
	}
}
