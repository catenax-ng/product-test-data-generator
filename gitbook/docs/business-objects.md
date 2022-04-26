---
sidebar_position: 3
title: Business Objects
---
There are three primary Business Objects managed with the TDG:
- Test Data Scenario: The formal definition of your testdata requirements.
- Test Data Template: A json-template that can be instanciated within your test data scenarios.
- Test Data Scenario Instance: A persistent generated result of your testdata scenario.
- Meta Model: JSON-Schema of a BAMM-Model.
- Vehicle Blueprint: Template-based approach to describe complex model-dependencies and tree-structures such as vehicles (or components).

## Test Data Scenario
The testdata scenario is the main object of the tdg and is the formal definition of your testdata requirements. To learn how to define those scenarios, please refer to the corresponding section [here](testdata-scenario)
- Attributes:
    - Name: name of the scenario
    - Version: version of the scenario
    - Content: code of the scenario (depending on type DSL or JavaScript)
    - ScriptType: DSL or JavaScript
    - ScriptStatus: 
        - *DECOMISSIONED*
        - *DRAFT*
        - *PRODUCTIVE*

Java documentation [here](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/model/TestDataScenario.html)

## Test Data Template
This describes a json-template (payload) of any given meta model

- Attributes:
    - Name: name of the template
    - Version: version of the template
    - Content: json payload

Java documentation [here](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/model/DataTemplate.html)

## Test Data Scenario Instance
Scenarios can be instanciated to be provided via API (rest interface, see [here](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/swagger/Swagger.html))

- Attributes:
    - Name: name of the instance
    - ScenarioID: reference to the original scenario (see above)
    - Content: json payload

Java documentation [here](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/model/TestDataScenarioInstance.html)

## Meta Model
A Meta Model describes the semantic model of an entity (or multiple / composits).

- Attributes:
    - Name: name of the meta-model
    - Version: version of the meta-model
    - Content: json-schema payload

Java documentation [here](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/model/MetaModel.html)

For further documentation of the current models and how to generate the artefacts (e. g. json-schema), please refer to the semantics team and take a look [here](https://github.com/catenax/BAMMmodels/) (github permissions required)

## Vehicle Blueprint
Please refer to this documentation section [here](testdata-scenario/vehicle-template)

Java documentation [here](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/testdata/blueprint/VehicleBlueprint.html)