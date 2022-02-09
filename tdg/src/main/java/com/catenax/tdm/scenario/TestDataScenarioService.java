package com.catenax.tdm.scenario;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.resource.TDMResourceLoader;
import com.catenax.tdm.util.JsonUtil;

public class TestDataScenarioService {

	private static final Logger log = LoggerFactory.getLogger(TestDataScenarioService.class);

	public static TestDataScenarioExecutor parseScenarioFromFile(String fname) throws Exception {
		CharStream stream = CharStreams.fromFileName(fname);
		return parseScenario(stream);
	}
	
	public static TestDataScenarioExecutor parseScenarioFromResource(String fname) throws Exception {
		String content = TDMResourceLoader.resourceToString(fname);
		return parseScenarioFromString(content);
	}
	
	private static String _fixContent(String content) {
		return JsonUtil.fixContent(content);
	}

	public static TestDataScenarioExecutor parseScenarioFromString(String content) throws Exception {
		log.info(content);
		
		String c = content;
		
		c = _fixContent(c);
		
		c = c
			.replaceAll("\n", "")
			.replaceAll("\r", "")
			.replaceAll("\t+", " ")
			;
		
		CharStream stream = CharStreams.fromString(c);
		return parseScenario(stream);
	}

	private static TestDataScenarioExecutor parseScenario(CharStream stream) throws Exception {
		Lexer lexer = new TestDataScenarioLexer(stream);

		TokenStream tokenStream = new CommonTokenStream(lexer);

		TestDataScenarioParser parser = new TestDataScenarioParser(tokenStream);

		ParseTree tree = parser.prog();
		ParseTreeWalker walker = new ParseTreeWalker();

		TestDataScenarioExecutor executor = new TestDataScenarioExecutor();
		walker.walk(executor, tree);

		return executor;
	}

}
