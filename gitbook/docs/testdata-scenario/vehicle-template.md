---
sidebar_position: 4
title: Vehicle Blueprint (json)
---

For Source Code definition please see [here](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/testdata/blueprint/VehicleBlueprint.html)

## Definition
This templating mechanism allows for a parent-child relatinship based definition of arbitrary models and their correlation.

### Vehicle Blueprint
The Blueprint has the following properties:
- name: Name of the blueprint
- version: Version of the blueprint
- parent: Parent Generation Item (root node)

[Java Doc](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/testdata/blueprint/VehicleBlueprint.html)

### Generation Item
This describes a parent-child relationship (1:n) between model instances.
Attributes of this entity:
- catenaXId: Describes the Catena-X specific uuid, if present (will be generated if nodeType == PARENT)
- comment: For important comments / inline documentation
- count: Number of instances of this item (including children)
- instanceId: Can be referenced via this id inside inline scripting (see below)
- modelName: Name of the metamodel to be used
- modelVersion: Version of the metamodel to be used
- templateName:  Name of the template to be used
- templateVersion: Version of the template to be used
- children (List): list of children, also of type Generation Item
- values (List): list of values to set after the instance is created (can use inline scripting, see below)
- code (List): list of inline script javascript code to be executed after this item will be internaly generated and values are set (e. g. to manipulate values)

[Java Doc](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/testdata/blueprint/GenerationItem.html)

## Inline Scripting
You can execute javascript and calculate value-assignments programaticaly to any attribute or in the code-section of the blueprint.
- *'!'* will execute arbitrary javascript
- *'#'* will reference constances defined in the parent
- *'$'* will self-reflect and address values of the current item

## Example
Thist example demonstrates the quick use of vehicle blueprints

### Test Data Scenario Example
Create a new Scenario of type Javascript and input the following code:
```javascript
// javascript 
// Name: OEMVehicleTemplate
// Version: 2.0

// This disables the auto-output of entity-grouped json-elements
// only the blueprint containers per part will be generated
scenario.setAutoAddTestdata(false);

// This enables the creation of a test-aas per part from a given aas template version
scenario.setAutoAddAas("aas", "1.0");

// This takes the actual vehicle blueprint template (name, version) and instanciates its content
scenario.generateFromVehicleTemplate("OEMVehicleTemplate", "2.0");


```

### Vehicle Blueprint Example
This is a complex and dynamic example of an actual blueprint definition (early stage) to give you some basic understanding and ideas how to manipulate entries and attributes:

```json
{
  "parent": {
    "instanceId": "CONST",
    "values": {
      "AUTHOR": "christian.kabelin@partner.bmw.de",
      "CREATION_DATE": "!rand.formatDate(rand.getToday())",
      "BPN_OEM_A": "BPNL00000003AYRE",
      "BPN_OEM_B": "BPNL00000003AVTH",
      "BPN_OEM_C": "BPNL00000003AZQP",
      "BPN_DISMANTLER": "BPNL00000003B6LU",
      "BPN_TIER_A": "BPNL00000003B2OM",
      "BPN_SUB_TIER_A": "BPNL00000003B0Q0",
      "BPN_TIER_B": "BPNL00000003B5MJ",
      "BPN_N-TIER_A": "BPNL00000003B0Q0",
      "BPN_SUB_TIER_B": "BPNL00000003AXS3",
      "BPN_IRS_TEST": "BPNL00000003AWSS",
      "BATCH_SEALANT_1": "!rand.getUuid()",
      "BATCH_SEALANT_2": "!rand.getUuid()",
      "BATCH_CATHODE_1": "!rand.getUuid()",
      "BATCH_CATHODE_2": "!rand.getUuid()",
      "BATCH_GLUE_1": "!rand.getUuid()",
      "BATCH_GLUE_2": "!rand.getUuid()",
      "BATCH_POLYAMID_1": "!rand.getUuid()",
      "BATCH_POLYAMID_2": "!rand.getUuid()"
    },
    "children": [
      {
        "modelName": "SerialPartTypization",
        "useId": true,
        "templateName": "spt_oem_vehicle",
        "children": [
          {
            "modelName": "MaterialForRecycling",
            "useId": false,
            "templateName": "matForRecyclingVehicleA",
            "children": [],
            "modelVersion": "1.0.0",
            "values": {},
            "count": 1,
            "useTemplate": true,
            "comment": "",
            "templateVersion": "1.0",
            "nodeType": "CHILD"
          },
          {
            "modelName": "AssemblyPartRelationship",
            "useId": true,
            "templateName": "apr",
            "children": [
              {
                "modelName": "SerialPartTypization",
                "modelVersion": "1.0.0",
                "templateName": "spt_clutch",
                "templateVersion": "1.0",
                "useId": true,
                "useTemplate": true,
                "nodeType": "PARENT",
                "count": 1,
                "comment": "",
                "children": [
                  {
                    "modelName": "PhysicalDimension",
                    "useId": false,
                    "templateName": "",
                    "children": [],
                    "modelVersion": "1.0.0",
                    "values": {
                      "diameter": null,
                      "length": null,
                      "width": null,
                      "weight": 85.568,
                      "height": null
                    },
                    "count": 1,
                    "useTemplate": false,
                    "comment": "",
                    "templateVersion": "",
                    "nodeType": "CHILD"
                  },
                  {
                    "modelName": "MaterialForRecycling",
                    "useId": false,
                    "templateName": "matForRecyclingClutch",
                    "children": [],
                    "modelVersion": "1.0.0",
                    "values": {},
                    "count": 1,
                    "useTemplate": true,
                    "comment": "",
                    "templateVersion": "1.0",
                    "nodeType": "CHILD"
                  },
                  {
                    "modelName": "AssemblyPartRelationship",
                    "modelVersion": "1.0.0",
                    "templateName": "apr",
                    "templateVersion": "1.0",
                    "useId": true,
                    "useTemplate": true,
                    "nodeType": "CHILD",
                    "count": 1,
                    "comment": "",
                    "values": {},
                    "children": [
                      {
                        "modelName": "SerialPartTypization",
                        "modelVersion": "1.0.0",
                        "templateName": "spt_polyamid",
                        "templateVersion": "1.0",
                        "useId": true,
                        "useTemplate": true,
                        "nodeType": "PARENT",
                        "count": 1,
                        "comment": "",
                        "children": [
                          {
                            "modelName": "MaterialForRecycling",
                            "useId": false,
                            "templateName": "MaterialForRecyclingMin",
                            "children": [],
                            "modelVersion": "1.0.0",
                            "values": {
                              "component[0].recycledContent": null,
                              "component[0].materialName": "Polyamid",
                              "component[0].materialClass": "5.5.1",
                              "component[0].aggregateState": null,
                              "component[0].weight": 0.2014,
                              "component[0].materialAbbreviation": "POL6"
                            },
                            "count": 1,
                            "useTemplate": true,
                            "comment": "",
                            "templateVersion": "1.0",
                            "nodeType": "CHILD"
                          }
                        ],
                        "values": {
                          "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                          "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[0].value": "!BPN_N-TIER_A",
                          "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                        }
                      },
                      {
                        "modelName": "SerialPartTypization",
                        "modelVersion": "1.0.0",
                        "templateName": "spt_sensor",
                        "templateVersion": "1.0",
                        "useId": true,
                        "useTemplate": true,
                        "nodeType": "PARENT",
                        "count": 1,
                        "comment": "",
                        "children": [
                          {
                            "modelName": "AssemblyPartRelationship",
                            "modelVersion": "1.0.0",
                            "templateName": "apr",
                            "templateVersion": "1.0",
                            "useId": true,
                            "useTemplate": true,
                            "nodeType": "CHILD",
                            "count": 1,
                            "comment": "",
                            "values": {},
                            "children": [
                              {
                                "modelName": "SerialPartTypization",
                                "modelVersion": "1.0.0",
                                "templateName": "spt_polyamid",
                                "templateVersion": "1.0",
                                "useId": true,
                                "useTemplate": true,
                                "nodeType": "PARENT",
                                "count": 1,
                                "comment": "",
                                "children": [
                                  {
                                    "modelName": "MaterialForRecycling",
                                    "useId": false,
                                    "templateName": "MaterialForRecyclingMin",
                                    "children": [],
                                    "modelVersion": "1.0.0",
                                    "values": {
                                      "component[0].recycledContent": null,
                                      "component[0].materialName": "Polyamid",
                                      "component[0].materialClass": "5.5.1",
                                      "component[0].aggregateState": null,
                                      "component[0].weight": 0.1908,
                                      "component[0].materialAbbreviation": "POL6"
                                    },
                                    "count": 1,
                                    "useTemplate": true,
                                    "comment": "",
                                    "templateVersion": "1.0",
                                    "nodeType": "CHILD"
                                  }
                                ],
                                "values": {
                                  "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                                  "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                                  "localIdentifiers[0].value": "BPN_N-TIER_A",
                                  "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                                }
                              }
                            ],
                            "code": [
                              "vehicleItem.replaceAprChildren(item, subparents);",
                              "item.get('childParts').get(0).get('quantity').put('quantityNumber', '0.1908');",
                              "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                              "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');"
                            ]
                          }
                        ],
                        "values": {
                          "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                          "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[0].value": "!BPN_SUB_TIER_A",
                          "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                        }
                      },
                      {
                        "modelName": "SerialPartTypization",
                        "modelVersion": "1.0.0",
                        "templateName": "spt_glue",
                        "templateVersion": "1.0",
                        "useId": true,
                        "useTemplate": true,
                        "nodeType": "PARENT",
                        "count": 1,
                        "comment": "",
                        "children": [
                          {
                            "modelName": "AssemblyPartRelationship",
                            "modelVersion": "1.0.0",
                            "templateName": "apr",
                            "templateVersion": "1.0",
                            "useId": true,
                            "useTemplate": true,
                            "nodeType": "CHILD",
                            "count": 1,
                            "comment": "",
                            "values": {},
                            "children": [
                              {
                                "modelName": "MaterialForRecycling",
                                "useId": false,
                                "templateName": "MaterialForRecyclingMin",
                                "children": [],
                                "modelVersion": "1.0.0",
                                "values": {
                                  "component[0].recycledContent": null,
                                  "component[0].materialName": "Glue",
                                  "component[0].materialClass": "6.2",
                                  "component[0].aggregateState": null,
                                  "component[0].weight": 0.2341,
                                  "component[0].materialAbbreviation": "GL338"
                                },
                                "count": 1,
                                "useTemplate": true,
                                "comment": "",
                                "templateVersion": "1.0",
                                "nodeType": "CHILD"
                              }
                            ],
                            "code": [
                              "vehicleItem.replaceAprChildren(item, subparents);"
                            ]
                          }
                        ],
                        "values": {
                          "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                          "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[0].value": "!BPN_SUB_TIER_B",
                          "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                        }
                      }
                    ],
                    "code": [
                      "vehicleItem.replaceAprChildren(item, subparents);",
                      "item.get('childParts').get(0).get('quantity').put('quantityNumber', '0.2014');",
                      "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                      "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');",
                      "item.get('childParts').get(2).get('quantity').put('quantityNumber', '0.2341');",
                      "item.get('childParts').get(2).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                      "item.get('childParts').get(2).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');"
                    ]
                  }
                ],
                "values": {
                  "localIdentifiers[0].value": "!BPN_TIER_A",
                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                }
              },
              {
                "modelName": "SerialPartTypization",
                "modelVersion": "1.0.0",
                "templateName": "spt_ecu",
                "templateVersion": "1.0",
                "useId": true,
                "useTemplate": true,
                "nodeType": "PARENT",
                "count": 1,
                "comment": "",
                "children": [
                  {
                    "modelName": "MaterialForRecycling",
                    "useId": false,
                    "templateName": "matForRecyclingECU",
                    "children": [],
                    "modelVersion": "1.0.0",
                    "values": {},
                    "count": 1,
                    "useTemplate": true,
                    "comment": "",
                    "templateVersion": "1.0",
                    "nodeType": "CHILD"
                  },
                  {
                    "modelName": "AssemblyPartRelationship",
                    "modelVersion": "1.0.0",
                    "templateName": "apr",
                    "templateVersion": "1.0",
                    "useId": true,
                    "useTemplate": true,
                    "nodeType": "CHILD",
                    "count": 1,
                    "comment": "",
                    "children": [
                      {
                        "modelName": "SerialPartTypization",
                        "modelVersion": "1.0.0",
                        "templateName": "spt_glue",
                        "templateVersion": "1.0",
                        "useId": true,
                        "useTemplate": true,
                        "nodeType": "PARENT",
                        "count": 1,
                        "comment": "",
                        "children": [
                          {
                            "modelName": "MaterialForRecycling",
                            "useId": false,
                            "templateName": "MaterialForRecyclingMin",
                            "children": [],
                            "modelVersion": "1.0.0",
                            "values": {
                              "component[0].recycledContent": null,
                              "component[0].materialName": "Glue",
                              "component[0].materialClass": "6.2",
                              "component[0].aggregateState": null,
                              "component[0].weight": 0.3301,
                              "component[0].materialAbbreviation": "GL338"
                            },
                            "count": 1,
                            "useTemplate": true,
                            "comment": "",
                            "templateVersion": "1.0",
                            "nodeType": "CHILD"
                          }
                        ],
                        "values": {
                          "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                          "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[0].value": "!BPN_SUB_TIER_B",
                          "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                        }
                      },
                      {
                        "modelName": "SerialPartTypization",
                        "modelVersion": "1.0.0",
                        "templateName": "spt_sensor",
                        "templateVersion": "1.0",
                        "useId": true,
                        "useTemplate": true,
                        "nodeType": "PARENT",
                        "count": 1,
                        "comment": "",
                        "children": [
                          {
                            "modelName": "AssemblyPartRelationship",
                            "modelVersion": "1.0.0",
                            "templateName": "apr",
                            "templateVersion": "1.0",
                            "useId": true,
                            "useTemplate": true,
                            "nodeType": "CHILD",
                            "count": 1,
                            "comment": "",
                            "values": {},
                            "children": [
                              {
                                "modelName": "SerialPartTypization",
                                "modelVersion": "1.0.0",
                                "templateName": "spt_polyamid",
                                "templateVersion": "1.0",
                                "useId": true,
                                "useTemplate": true,
                                "nodeType": "PARENT",
                                "count": 1,
                                "comment": "",
                                "children": [
                                  {
                                    "modelName": "MaterialForRecycling",
                                    "useId": false,
                                    "templateName": "MaterialForRecyclingMin",
                                    "children": [],
                                    "modelVersion": "1.0.0",
                                    "values": {
                                      "component[0].recycledContent": null,
                                      "component[0].materialName": "Polyamid",
                                      "component[0].materialClass": "5.5.1",
                                      "component[0].aggregateState": null,
                                      "component[0].weight": 0.1908,
                                      "component[0].materialAbbreviation": "POL6"
                                    },
                                    "count": 1,
                                    "useTemplate": true,
                                    "comment": "",
                                    "templateVersion": "1.0",
                                    "nodeType": "CHILD"
                                  }
                                ],
                                "values": {
                                  "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                                  "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                                  "localIdentifiers[0].value": "!BPN_N-TIER_A",
                                  "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                                }
                              }
                            ],
                            "code": [
                              "vehicleItem.replaceAprChildren(item, subparents);",
                              "item.get('childParts').get(0).get('quantity').put('quantityNumber', '0.1908');",
                              "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                              "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');"
                            ]
                          }
                        ],
                        "values": {
                          "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                          "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[0].value": "!BPN_SUB_TIER_A",
                          "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                        }
                      },
                      {
                        "modelName": "SerialPartTypization",
                        "modelVersion": "1.0.0",
                        "templateName": "spt_polyamid",
                        "templateVersion": "1.0",
                        "useId": true,
                        "useTemplate": true,
                        "nodeType": "PARENT",
                        "count": 1,
                        "comment": "",
                        "children": [
                          {
                            "modelName": "MaterialForRecycling",
                            "useId": false,
                            "templateName": "MaterialForRecyclingMin",
                            "children": [],
                            "modelVersion": "1.0.0",
                            "values": {
                              "component[0].recycledContent": null,
                              "component[0].materialName": "Polyamid",
                              "component[0].materialClass": "5.5.1",
                              "component[0].aggregateState": null,
                              "component[0].weight": 0.2001,
                              "component[0].materialAbbreviation": "POL6"
                            },
                            "count": 1,
                            "useTemplate": true,
                            "comment": "",
                            "templateVersion": "1.0",
                            "nodeType": "CHILD"
                          }
                        ],
                        "values": {
                          "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                          "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[0].value": "!BPN_N-TIER_A",
                          "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                          "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                        }
                      }
                    ],
                    "code": [
                      "vehicleItem.replaceAprChildren(item, subparents);",
                      "item.get('childParts').get(0).get('quantity').put('quantityNumber', '0.3301');",
                      "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                      "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');",
                      "item.get('childParts').get(2).get('quantity').put('quantityNumber', '0.2001');",
                      "item.get('childParts').get(2).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                      "item.get('childParts').get(2).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');"
                    ]
                  }
                ],
                "values": {
                  "partTypeInformation.manufacturerPartID": "!rand.getPattern('99999C9/-99')",
                  "partTypeInformation.customerPartId": "$partTypeInformation.manufacturerPartID",
                  "localIdentifiers[0].value": "!BPN_TIER_B",
                  "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                }
              },
              {
                "modelName": "SerialPartTypization",
                "useId": true,
                "templateName": "spt_oem_battery",
                "children": [
                  {
                    "modelName": "MaterialForRecycling",
                    "useId": false,
                    "templateName": "matForRecyclingBatteryA",
                    "children": [],
                    "modelVersion": "1.0.0",
                    "values": {},
                    "count": 1,
                    "useTemplate": true,
                    "comment": "",
                    "templateVersion": "1.0",
                    "nodeType": "CHILD"
                  },
                  {
                    "modelName": "AssemblyPartRelationship",
                    "useId": true,
                    "templateName": "apr",
                    "children": [
                      {
                        "modelName": "SerialPartTypization",
                        "useId": true,
                        "templateName": "spt_cellmodule",
                        "children": [
                          {
                            "modelName": "MaterialForRecycling",
                            "useId": false,
                            "templateName": "matForRecyclingBatteryModuleA",
                            "children": [],
                            "modelVersion": "1.0.0",
                            "values": {},
                            "count": 1,
                            "useTemplate": true,
                            "comment": "",
                            "templateVersion": "1.0",
                            "nodeType": "CHILD"
                          },
                          {
                            "modelName": "AssemblyPartRelationship",
                            "useId": true,
                            "templateName": "apr",
                            "children": [
                              {
                                "modelName": "SerialPartTypization",
                                "modelVersion": "1.0.0",
                                "templateName": "spt_sealant",
                                "templateVersion": "1.0",
                                "useTemplate": true,
                                "comment": "",
                                "useId": true,
                                "count": 1,
                                "nodeType": "PARENT",
                                "catenaXId": "!rand.getAny(BATCH_SEALANT_1, BATCH_SEALANT_2)",
                                "values": {
                                  "localIdentifiers[0].value": "!BPN_SUB_TIER_B",
                                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                                },
                                "children": [
                                  {
                                    "modelName": "AssemblyPartRelationship",
                                    "useId": true,
                                    "templateName": "apr",
                                    "modelVersion": "1.0.0",
                                    "values": {
                                      "childParts": null
                                    },
                                    "children": [
                                      {
                                        "modelName": "MaterialForRecycling",
                                        "useId": false,
                                        "templateName": "MaterialForRecyclingMin",
                                        "children": [],
                                        "modelVersion": "1.0.0",
                                        "values": {
                                          "component[0].recycledContent": null,
                                          "component[0].materialName": "Sealant",
                                          "component[0].materialClass": "6.3",
                                          "component[0].aggregateState": null,
                                          "component[0].weight": 0.11,
                                          "component[0].materialAbbreviation": "SEL3321"
                                        },
                                        "count": 1,
                                        "useTemplate": true,
                                        "comment": "",
                                        "templateVersion": "1.0",
                                        "nodeType": "CHILD"
                                      }
                                    ],
                                    "count": 1,
                                    "useTemplate": true,
                                    "comment": "",
                                    "templateVersion": "1.0",
                                    "nodeType": "CHILD"
                                  }
                                ]
                              },
                              {
                                "modelName": "SerialPartTypization",
                                "modelVersion": "1.0.0",
                                "templateName": "spt_cathode",
                                "templateVersion": "1.0",
                                "useTemplate": true,
                                "comment": "",
                                "useId": true,
                                "count": 1,
                                "nodeType": "PARENT",
                                "values": {
                                  "localIdentifiers[0].value": "!BPN_N-TIER_A",
                                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                                },
                                "children": [
                                  {
                                    "modelName": "AssemblyPartRelationship",
                                    "useId": true,
                                    "templateName": "apr",
                                    "modelVersion": "1.0.0",
                                    "values": {
                                      "childParts": null
                                    },
                                    "children": [
                                      {
                                        "modelName": "MaterialForRecycling",
                                        "useId": false,
                                        "templateName": "MaterialForRecyclingMin",
                                        "children": [],
                                        "modelVersion": "1.0.0",
                                        "values": {
                                          "component[0].recycledContent": null,
                                          "component[0].materialName": "Iron",
                                          "component[0].materialClass": "1.1",
                                          "component[0].aggregateState": null,
                                          "component[0].weight": 0.1204,
                                          "component[0].materialAbbreviation": "N6C6M2ZYX"
                                        },
                                        "count": 1,
                                        "useTemplate": true,
                                        "comment": "",
                                        "templateVersion": "1.0",
                                        "nodeType": "CHILD"
                                      }
                                    ],
                                    "count": 1,
                                    "useTemplate": true,
                                    "comment": "",
                                    "templateVersion": "1.0",
                                    "nodeType": "CHILD"
                                  }
                                ]
                              },
                              {
                                "modelName": "SerialPartTypization",
                                "useId": true,
                                "templateName": "spt_cell",
                                "children": [
                                  {
                                    "modelName": "AssemblyPartRelationship",
                                    "useId": true,
                                    "templateName": "apr",
                                    "children": [],
                                    "modelVersion": "1.0.0",
                                    "values": {
                                      "childParts": null
                                    },
                                    "count": 1,
                                    "useTemplate": true,
                                    "comment": "",
                                    "templateVersion": "1.0",
                                    "nodeType": "CHILD"
                                  },
                                  {
                                    "modelName": "PhysicalDimension",
                                    "useId": false,
                                    "templateName": "",
                                    "children": [],
                                    "modelVersion": "1.0.0",
                                    "values": {
                                      "diameter": null,
                                      "length": null,
                                      "width": null,
                                      "weight": 1.4688,
                                      "height": null
                                    },
                                    "count": 1,
                                    "useTemplate": false,
                                    "comment": "",
                                    "templateVersion": "",
                                    "nodeType": "CHILD"
                                  },
                                  {
                                    "modelName": "BatteryProductDescription",
                                    "useId": false,
                                    "templateName": "BatteryProductDescription",
                                    "children": [],
                                    "modelVersion": "1.0.1",
                                    "values": {
                                      "type": "HVB",
                                      "minimalStateOfHealth.minimalStateOfHealthValue": 90,
                                      "currentStateOfHealth[0].currentStateOfHealthTimestamp": "!rand.formatDate(rand.getPastDate(10 * 365))",
                                      "currentStateOfHealth[0].currentStateOfHealthValue": "105",
                                      "currentStateOfHealth[1].currentStateOfHealthTimestamp": "!rand.formatDate(rand.getPastDate(31))",
                                      "currentStateOfHealth[1].currentStateOfHealthValue": "95",
                                      "performanceIndicator.electricCapacityMin": 0.8,
                                      "performanceIndicator.electricCapacityMax": 0.88
                                    },
                                    "count": 1,
                                    "useTemplate": true,
                                    "comment": "",
                                    "templateVersion": "1.0",
                                    "nodeType": "CHILD"
                                  }
                                ],
                                "modelVersion": "1.0.0",
                                "values": {
                                  "localIdentifiers[0].value": "!BPN_SUB_TIER_B",
                                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                                },
                                "count": 10,
                                "useTemplate": true,
                                "comment": "",
                                "templateVersion": "1.0",
                                "nodeType": "PARENT"
                              }
                            ],
                            "modelVersion": "1.0.0",
                            "values": {},
                            "count": 1,
                            "useTemplate": true,
                            "comment": "",
                            "templateVersion": "1.0",
                            "nodeType": "CHILD",
                            "code": [
                              "vehicleItem.replaceAprChildren(item, subparents);",
                              "item.get('childParts').get(0).get('quantity').put('quantityNumber', 0.11);",
                              "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                              "item.get('childParts').get(0).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');",
                              "item.get('childParts').get(1).get('quantity').put('quantityNumber', 0.1204);",
                              "item.get('childParts').get(1).get('quantity').get('measurementUnit').put('lexicalValue', 'kilogram');",
                              "item.get('childParts').get(1).get('quantity').get('measurementUnit').put('datatypeURI', 'urn:bamm:io.openmanufacturing:meta-model:1.0.0#kilogram');"
                            ]
                          }
                        ],
                        "modelVersion": "1.0.0",
                        "values": {
                          "localIdentifiers[0].value": "!vehicleItem.var('oem')",
                          "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                        },
                        "count": 6,
                        "useTemplate": true,
                        "comment": "",
                        "templateVersion": "1.0",
                        "nodeType": "PARENT"
                      }
                    ],
                    "modelVersion": "1.0.0",
                    "values": {},
                    "count": 1,
                    "useTemplate": true,
                    "comment": "",
                    "templateVersion": "1.0",
                    "nodeType": "CHILD",
                    "code": [
                      "vehicleItem.replaceAprChildren(item, subparents);"
                    ]
                  }
                ],
                "modelVersion": "1.0.0",
                "values": {
                  "localIdentifiers[0].value": "!vehicleItem.var('oem')",
                  "localIdentifiers[2].value": "!rand.getPattern('/N/O/-999999999999999999999999')"
                },
                "count": 1,
                "useTemplate": true,
                "comment": "",
                "templateVersion": "1.0",
                "nodeType": "PARENT"
              }
            ],
            "modelVersion": "1.0.0",
            "values": {},
            "count": 1,
            "useTemplate": true,
            "comment": "",
            "templateVersion": "1.0",
            "nodeType": "CHILD",
            "code": [
              "vehicleItem.replaceAprChildren(item, subparents);"
            ]
          }
        ],
        "modelVersion": "1.0.0",
        "values": {
          "partTypeInformation.manufacturerPartID": "!rand.getPattern('CC/-99')",
          "localIdentifiers[0].value": "!var val = rand.getAny(BPN_OEM_A, BPN_OEM_B, BPN_OEM_C);vehicleItem.var('oem', val);val",
          "localIdentifiers[1].value": "$partTypeInformation.manufacturerPartID",
          "localIdentifiers[2].value": "!rand.getPattern('/O/E/M/-/A/-AAAAAAAAAAAAAAAAAAAA')",
          "localIdentifiers[3].value": "$localIdentifiers[2].value",
          "manufacturingInformation.date": "!rand.formatDate(rand.getDateBetween(rand.getDate(2012, 11, 16), rand.getDate(2020, 12, 31)))"
        },
        "count": 10,
        "useTemplate": true,
        "comment": "",
        "templateVersion": "1.0",
        "nodeType": "PARENT"
      }
    ],
    "nodeType": "PARENT"
  },
  "name": "Traceability and CE Testset 1",
  "version": "1.0"
}
```
