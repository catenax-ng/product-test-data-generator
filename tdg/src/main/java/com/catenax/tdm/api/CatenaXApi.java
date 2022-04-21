package com.catenax.tdm.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.catenax.tdm.deo.TestDataScenarioInstanceStatus;
import com.catenax.tdm.model.DataTemplate;
import com.catenax.tdm.model.MetaModel;
import com.catenax.tdm.model.TestDataScenario;
import com.catenax.tdm.model.TestDataScenario.TestDataScenarioStatus;
import com.catenax.tdm.model.TestDataScenario.TestDataScenarioType;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@Validated
public interface CatenaXApi {
	/*
	@Operation(summary = "get metamodel schema description", description = "Retrieve Metamodel Schema", tags = { "Metamodel" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Metamodel Schema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/metamodel/{model}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.GET)
	ResponseEntity<String> getModelDescription(
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("model") String model,
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("version") String version
			);
	*/
	
	@Operation(summary = "Generate test data for schema", description = "Generate test data for schema", tags = { "Testdata" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdata/{model}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.GET)
	ResponseEntity<String> getTestdata(
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("model") String model,
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("version") String version,
			@Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @RequestParam(value = "count", required = false) Integer count
			);
	
	@Operation(summary = "Get TestData Scenario definition(s)", description = "Get TestData Scenario definition(s)", tags = { "Testdata Scenario" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/{scenario}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	ResponseEntity<List<TestDataScenario>> getTestdataScenario(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("scenario") String scenario,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version
			);
	
	@Operation(summary = "Create TestData Scenario definition(s)", description = "Create TestData Scenario definition(s)", tags = { "Testdata Scenario" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.POST)
	ResponseEntity<TestDataScenario> createTestdataScenario(
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody TestDataScenario testDataScenario
			);
	
	@Operation(summary = "Update TestData Scenario definition(s)", description = "Update TestData Scenario definition(s)", tags = { "Testdata Scenario" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			consumes = {MediaType.APPLICATION_JSON_VALUE},  
			method = RequestMethod.PUT)
	ResponseEntity<TestDataScenario> updateTestdataScenario(
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody TestDataScenario data
			);
	
	@Operation(summary = "Update TestData Scenario content", description = "Update TestData Scenario definition(s)", tags = { "Testdata Scenario" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/{scenario}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			consumes = {MediaType.APPLICATION_JSON_VALUE},  
			method = RequestMethod.PUT)
	ResponseEntity<TestDataScenario> updateTestdataScenarioContent(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("scenario") String scenario,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version,
			@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @RequestParam(value = "scriptType", required = true) TestDataScenario.TestDataScenarioType scriptType,
			@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @RequestParam(value = "scriptStatus", required = true) TestDataScenario.TestDataScenarioStatus scriptStatus,
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody String content
			);
	
	@Operation(summary = "Delete TestData Scenario definition(s)", description = "Delete TestData Scenario definition(s)", tags = { "Testdata Scenario" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.DELETE)
	ResponseEntity<TestDataScenario> deleteTestdataScenario(
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody TestDataScenario data
			);
	
	@Operation(summary = "Instantiate or retrieve TestData Scenario", description = "Instantiate or retrieve TestData Scenario", tags = { "Testdata Scenario - Instance" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/{scenario}/{version}/instance/{name}", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.GET)
	ResponseEntity<String> instantiateTestdataScenario(
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("scenario") String scenario,
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("version") String version,
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("name") String name,
			@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @RequestParam(value = "overwrite", required = true) boolean overwrite,
			@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @RequestParam(value = "includeGraphQL", required = true) boolean includeGraphQL
			);
	
	@Operation(summary = "Instantiate TestData Scenario ad hoc", description = "Instantiate TestData Scenario ad hoc", tags = { "Testdata Scenario - Instance" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/instance/adhoc", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.POST)
	ResponseEntity<String> instantiateTestdataScenarioRaw(
			@Parameter(in = ParameterIn.QUERY, description = "", required = false, schema = @Schema()) @RequestParam(value = "scriptType") TestDataScenarioType scriptType,
			@RequestBody String script
			);
	
	@Operation(summary = "Delete TestData Scenario instance", description = "Delete TestData Scenario instance", tags = { "Testdata Scenario - Instance" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/{scenario}/{version}/instance/{name}", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.DELETE)
	ResponseEntity<Boolean> deleteTestdataScenarioInstance(
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("scenario") String scenario,
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("version") String version,
			@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("name") String name
			);
	
	@Operation(summary = "List TestData Scenario instances", description = "List TestData Scenario instances", tags = { "Testdata Scenario - Instance" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/instance/list", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.GET)
	ResponseEntity<List<TestDataScenarioInstanceStatus>> listTestdataScenarioInstances(
			@Parameter(in = ParameterIn.QUERY, description = "", required = false, schema = @Schema()) @RequestParam(value = "scenario") String scenario,
			@Parameter(in = ParameterIn.QUERY, description = "", required = false, schema = @Schema()) @RequestParam(value = "version") String version,
			@Parameter(in = ParameterIn.QUERY, description = "", required = false, schema = @Schema()) @RequestParam(value = "name") String name
			);
	
	@Operation(summary = "Query TestData Scenario instance", description = "Query TestData Scenario instance", tags = { "Testdata Scenario - Instance" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/instance/query", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.GET)
	ResponseEntity<String> queryTestdataScenarioInstances(
			@Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @RequestParam(value = "scenario") String scenario,
			@Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @RequestParam(value = "version") String version,
			@Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @RequestParam(value = "name") String name,
			@Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @RequestParam(value = "query") String query
			);
	
	@Operation(summary = "Set TestData Scenario status", description = "Set TestData Scenario status", tags = { "Testdata Scenario" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata Scenario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/testdatascenario/{scenario}/{version}/status/{status}", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	ResponseEntity<Boolean> setTestdataScenarioStatus(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("scenario") String scenario,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("status") TestDataScenarioStatus status
			);
	
	/* DataTemplate */
	
	@Operation(summary = "Get DataTemplate(s)", description = "Get DataTemplate(s)", tags = { "Data Template" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Data Template", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/datatemplate/{template}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	ResponseEntity<List<DataTemplate>> getDataTemplates(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("template") String template,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version
			);
	
	@Operation(summary = "Create DataTemplate", description = "Create DataTemplate", tags = { "Data Template" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Data Template", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/datatemplate", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	ResponseEntity<DataTemplate> createDataTemplate(
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody DataTemplate dataTemplate
			);
	
	@Operation(summary = "Update DataTemplate", description = "Update DataTemplate", tags = { "Data Template" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Data Template", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/datatemplate", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.PUT)
	ResponseEntity<DataTemplate> updateDataTemplate(
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody DataTemplate dataTemplate
			);
	
	@Operation(summary = "Update DataTemplate content", description = "Update DataTemplate content", tags = { "Data Template" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Data Template", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/datatemplate/{template}/{version}/content", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.PUT)
	ResponseEntity<DataTemplate> updateDataTemplateContent(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("template") String template,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version,
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody String content
			);
	
	
	@Operation(summary = "Delete DataTemplate", description = "Delete DataTemplate", tags = { "Data Template" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Data Template", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/datatemplate/{template}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.DELETE)
	ResponseEntity<Boolean> deleteDataTemplate(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("template") String template,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version
			);
	
	/* MetaModel */
	
	@Operation(summary = "Get MetaModel(s)", description = "Get MetaModel(s)", tags = { "Meta Model" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "MetaModel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/metamodel/{name}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	ResponseEntity<List<MetaModel>> getMetaModels(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("name") String name,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version
			);
	
	@Operation(summary = "Create MetaModel", description = "Create MetaModel", tags = { "Meta Model" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "MetaModel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/metamodel", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	ResponseEntity<MetaModel> createMetaModel(
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody MetaModel metaModel
			);
	
	@Operation(summary = "Update MetaModel", description = "Update MetaModel", tags = { "Meta Model" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "MetaModel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/metamodel", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.PUT)
	ResponseEntity<MetaModel> updateMetaModel(
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody MetaModel metaModel
			);
	
	@Operation(summary = "Update MetaModel content", description = "Update MetaModel content", tags = { "Meta Model" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "MetaModel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/metamodel/{name}/{version}/content", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.PUT)
	ResponseEntity<MetaModel> updateMetaModelContent(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("name") String name,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version,
			@ApiParam(value = "", required=true ) 
			@Valid @RequestBody String content
			);
	
	
	@Operation(summary = "Delete MetaModel", description = "Delete MetaModel", tags = { "Meta Model" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Data Template", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/metamodel/{name}/{version}", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.DELETE)
	ResponseEntity<Boolean> deleteMetaModel(
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("name") String name,
			@Parameter(in = ParameterIn.PATH, description = "", schema = @Schema()) @PathVariable("version") String version
			);
	
	// Test Server Function
	@Operation(summary = "Test function", description = "Test function", tags = { "Test" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Testdata", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))) })
	@RequestMapping(value = "/api/catena-x/tdm/1.1/test", 
			produces = {MediaType.APPLICATION_JSON_VALUE}, 
			method = RequestMethod.GET)
	ResponseEntity<String> test();
	
}
