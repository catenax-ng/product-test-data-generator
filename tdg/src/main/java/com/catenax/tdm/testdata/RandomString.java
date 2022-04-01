package com.catenax.tdm.testdata;

import java.util.Random;

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
	
	private static final Random RND = new Random();
	
	private static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String abc = ABC.toLowerCase();
	private static final String r123 = "0123456789";
	
	private static final String VOCALS = "AEIOU";
	private static final String vocals = VOCALS.toLowerCase();
	
	private static final String CONSONANTS = "BCDFGHIJKLMNPQRSTVWXYZ";
	private static final String consonants = CONSONANTS.toLowerCase();
	
	private static char rnd(String values) {
		return values.charAt(RND.nextInt(values.length()));
	}
	
	public static String fromPattern(String pattern) {
		StringBuilder sb = new StringBuilder();

		boolean real = false;
		for(char c : pattern.toCharArray()) {
		    if('/' == c) {
		    	real = true;
		    } else if(real) {
		    	real = false;
		    	sb.append(c);
		    } else {
		    	if('C' == c) {
		    		sb.append(rnd(CONSONANTS));
		    	} else if('c' == c) {
		    		sb.append(rnd(consonants));
		    	} else if('V' == c) {
		    		sb.append(rnd(VOCALS));
		    	} else if('v' == c) {
		    		sb.append(rnd(vocals));
		    	} else if('0' <= c && c <= '9') {
		    		sb.append(rnd(r123));
		    	} else if('A' <= c && c <= 'Z') {
		    		sb.append(rnd(ABC));
		    	} else if('a' <= c && c <= 'z') {
		    		sb.append(rnd(abc));
		    	} else {
		    		sb.append(c);
		    	}
		    }
		}

		return sb.toString();
	}
}
