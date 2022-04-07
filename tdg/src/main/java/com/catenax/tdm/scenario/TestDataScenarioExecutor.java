package com.catenax.tdm.scenario;

import java.util.Optional;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catenax.tdm.dao.MetaModelRepository;
import com.catenax.tdm.metamodel.MetaModelResourceRepository;
import com.catenax.tdm.model.MetaModel;
import com.catenax.tdm.scenario.TestDataScenarioParser.AssignContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.ConcatContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.DeclareContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.EntityContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.ForeachContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.GenerationContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.IncludeContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.InstanceContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.ProgContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.RandomContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.RetrieveContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.ScalarContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.StatementContext;
import com.catenax.tdm.scenario.TestDataScenarioParser.TemplateContext;
import com.catenax.tdm.scripting.ScriptEngine;
import com.catenax.tdm.testdata.TestDataGenerator;

public class TestDataScenarioExecutor extends TestDataScenarioBaseListener {

	private static final Logger log = LoggerFactory.getLogger(TestDataScenarioExecutor.class);
	private static final String NEWLINE = System.getProperty("line.separator");  
	private static final String END = ";" + NEWLINE; 
	
	private String name = "<none>";
	private String version = "<none>";
	
	private StringBuilder script = new StringBuilder("");

	private MetaModelResourceRepository metamodelRepository;
	private TestDataGenerator testdataGenerator;
	
	protected class CombinedMetamodelRepository implements MetaModelResourceRepository {
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

	public String execute(ScriptEngine engine, boolean includeGraphQL) {
		log.info("Execute TestDataScenario: '" + name + "' version " + version + " ...");
		
		String s = script.toString();

		String result = null;
		
		try {
			result = engine.executeScript(s, includeGraphQL).toString();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return result;
	}

	public void setMetamodelRepository(MetaModelRepository dbMetaModelRepository, MetaModelResourceRepository metamodelRepository) {
		// this.dbMetaModelRepository = dbMetaModelRepository;
		// this.metamodelRepository = metamodelRepository;
		this.metamodelRepository = new CombinedMetamodelRepository(dbMetaModelRepository, metamodelRepository);
	}
	
	public void setTestdataGenerator(TestDataGenerator testdataGenerator) {
		this.testdataGenerator = testdataGenerator;
	}
	
	// ===== BEGIN WALKER ===== //

	@Override
	public void enterProg(ProgContext ctx) {
		// TODO Auto-generated method stub
		super.enterProg(ctx);
	}

	@Override
	public void exitProg(ProgContext ctx) {
		this.name = ctx.getChild(2).toString();
		this.version = ctx.getChild(6).toString();
		super.exitProg(ctx);
	}

	@Override
	public void enterEntity(EntityContext ctx) {
		// TODO Auto-generated method stub
		super.enterEntity(ctx);
	}

	@Override
	public void exitEntity(EntityContext ctx) {
		String eName = ctx.getChild(4).toString();
		String eVersion = ctx.getChild(7).toString();
		String eAlias = ctx.getChild(10).toString();
		
		log.info("Add Entity '" + eName + "' version: " + eVersion + " as '" + eAlias + "'");

		script.append("var " + eAlias + " = scenario.getSchema('" + eName + "', '" + eVersion + "')" + END);
		
		super.exitEntity(ctx);
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		super.enterEveryRule(ctx);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		super.exitEveryRule(ctx);
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		// TODO Auto-generated method stub
		super.visitTerminal(node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		// TODO Auto-generated method stub
		super.visitErrorNode(node);
	}

	@Override
	public void enterGeneration(GenerationContext ctx) {
		// TODO Auto-generated method stub
		super.enterGeneration(ctx);
	}

	@Override
	public void exitGeneration(GenerationContext ctx) {
		String gName = ctx.getChild(1).toString();
		String gTimes = ctx.getChild(3).toString();
		String gAlias = ctx.getChild(6).toString();

		script.append("var " + gAlias + " = scenario.generateTestData(" + gName + ", " + gTimes + ")" + END);
		
		super.exitGeneration(ctx);
	}
	
	@Override
	public void enterInstance(InstanceContext ctx) {
		String entity = ctx.getChild(1).toString();
		String name = ctx.getChild(4).toString();
		
		log.info(" => Instance " + name + " as " + entity);
		script.append("var " + name + " = scenario.generateTestData(" + entity);

		super.enterInstance(ctx);
	}
	
	@Override
	public void exitInstance(InstanceContext ctx) {
		script.append(")" + END);

		super.exitInstance(ctx);
	}
	
	@Override
	public void exitTemplate(TemplateContext ctx) {
		String name = ctx.getChild(1).toString();
		String version = ctx.getChild(2).toString();
		
		log.info("   ==> Template " + name + " " + version);
		script.append(", '" + name + "', '" + version + "'");
		
		super.exitTemplate(ctx);
	}

	@Override
	public void enterAssign(AssignContext ctx) {
		String var = ctx.getChild(1).toString();
		String attr = ctx.getChild(3).toString();

		script.append(var + ".put('" + attr + "', ");

		super.exitAssign(ctx);
	}
	
	@Override
	public void exitAssign(AssignContext ctx) {
		script.append(")" + END);
		super.exitAssign(ctx);
	}
	
	
	
	@Override
	public void enterForeach(ForeachContext ctx) {
		String var = ctx.getChild(4).toString();
		String arr = ctx.getChild(1).toString();
		
		String itVar = "iterator_index";

		script.append("for(var " + itVar + " = 0; " + itVar + " < " + arr + ".length(); " + itVar + "++) {" + NEWLINE);
		script.append("\tvar " + var + " = " + arr + ".get(" + itVar + ")" + END);
		script.append("\tlog.info('iterator: ' + " + itVar + ")" + END);

		super.enterForeach(ctx);
	}
	
	@Override
	public void exitForeach(ForeachContext ctx) {
		String var = ctx.getChild(4).toString();
		String arr = ctx.getChild(1).toString();
		
		script.append("}" + END);

		super.exitForeach(ctx);
	}
	
	@Override
	public void enterDeclare(DeclareContext ctx) {
		String var = ctx.getChild(1).toString();
		script.append("var " + var + " = ");
		super.exitDeclare(ctx);
	}
	
	@Override
	public void exitDeclare(DeclareContext ctx) {
		script.append(END);
		super.exitDeclare(ctx);
	}
	
	@Override
	public void exitRetrieve(RetrieveContext ctx) {
		String val = ctx.getChild(0).toString();
		for(int i = 2; i < ctx.getChildCount(); i += 2) {
			String c = ctx.getChild(i).toString();
			val += ".get('" + c + "')";
		}
		
		script.append(val);
		
		super.exitRetrieve(ctx);
	}
	
	@Override
	public void exitRandom(RandomContext ctx) {
		String val = ctx.getChild(1).toString();
		script.append("rand.get" + val);
		
		if(ctx.getChildCount() > 2) {
			script.append("Between(" + ctx.getChild(3).toString() + ", " + ctx.getChild(5).toString() + ")");
		} else {
			script.append("()");
		}
		
		super.exitRandom(ctx);
	}
	
	@Override
	public void exitScalar(ScalarContext ctx) {
		for(int i = 0; i < ctx.getChildCount(); i++) {
			script.append(ctx.getChild(i).toString());
		}
		super.exitScalar(ctx);
	}
	
	@Override
	public void enterConcat(ConcatContext ctx) {
		script.append(" + ");
		super.enterConcat(ctx);
	}

	@Override
	public void exitInclude(IncludeContext ctx) {
		String script = ctx.getChild(1).toString();
		String version = ctx.getChild(2).toString();
		
		log.info("Include script: " + script + " v" + version);
		log.error("Feature 'include' not yet supported!");
		
		super.exitInclude(ctx);
	}

	@Override
	public void exitStatement(StatementContext ctx) {
		log.info("=> Statement: " + ctx.getText());
	}

}
