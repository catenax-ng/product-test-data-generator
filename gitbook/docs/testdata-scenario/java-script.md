---
sidebar_position: 3
title: Testdata Scenarios (JavaScript)
---
## Definition
This allows you to freely define a testdata scenario as javascript. Any generated instances (plain or with template) are automatically added to the output.

## Important variables
- *scenario*: generate instances from meta models and templates [JavaDoc](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/scenario/TestDataScenarioFactory.html)
- *rand*: generate random strings / numbers [JavaDoc](https://catenax-ng.github.io/product-test-data-generator/javadoc/tdg/doc/com/catenax/tdm/testdata/TestDataFactory.html)
- *log*: Standard logger for the component

## Examples
The following examples show how to use the javascript method and you can use them to re-use the methods called in those examples to build your own testdata examples.
### Generic Structure Example
```javascript
// javascript 
// Name: cetest
// Version: 1.0

scenario.setAutoAddTestdata(false);

var BusinessPartner = scenario.getSchema('BPDM', '1.1.0');
// var bmw = scenario.generateTestData(BusinessPartner, 'bp_bmw', '1.0');

// var Traceability = scenario.getSchema('Traceability', '0.1.1');
var SerialPartTypization = scenario.getSchema('SerialPartTypization', '1.0.0');
var AssemblyPartRelationship = scenario.getSchema('AssemblyPartRelationship', '1.0.0');
// var Material = scenario.getSchema('Material', '0.1.1');
// var TechnicalData = scenario.getSchema('TechnicalData', '0.1.1');

var ProductDescription = scenario.getSchema('ProductDescription', '1.0.0');
var MaterialForRecycling = scenario.getSchema('MaterialForRecycling', '1.0.0');

var cellUuid = rand.getUuid();

var vehicleQuantity = 10;
var hvbQuantity = 1;
var moduleQuantity  = 6;
var cellQuantity    = 10;

for(var i = 0; i < vehicleQuantity; i++) {
        var spr_vehicle = scenario.generateTestData(SerialPartTypization, 'spt_vehicle', '1.0');

        var van = rand.getVan();
        spr_vehicle.get('localIdentifiers').get(2).put('value', van);
        spr_vehicle.get('localIdentifiers').get(3).put('value', van);

        var uuid = rand.getUuid();
        spr_vehicle.put('catenaXId', uuid);
        var apr_vehicle = scenario.generateTestData(AssemblyPartRelationship, 'apr', '1.0');
        apr_vehicle.put('catenaXId', spr_vehicle.get('catenaXId'));
        
        var moduleChild = apr_vehicle.get('childParts').get(0);
        apr_vehicle.get('childParts').remove(0);
        
        var pd_vehicle = scenario.generateTestData(ProductDescription);
        var mf_vehicle = scenario.generateTestData(MaterialForRecycling);
        
        var con_vehicle = scenario.generateTestDataContainer(uuid);
        scenario.addToContainer(con_vehicle, apr_vehicle, AssemblyPartRelationship);
        scenario.addToContainer(con_vehicle, spr_vehicle, SerialPartTypization);

        for(var h = 0; h < hvbQuantity; h++) {
            var hvbUuid = rand.getUuid();
            var hvbSerialNo = rand.getSerialNoAlphaNum(28);
            
            var moduleChildInst = cloneElement(moduleChild);
            moduleChildInst.put('childCatenaXId', hvbUuid);
            apr_vehicle.get('childParts').put(moduleChildInst);
            
            var spr_hvb = scenario.generateTestData(SerialPartTypization, 'spt_hvb', '1.0');
            var apr_hvb = scenario.generateTestData(AssemblyPartRelationship, 'apr', '1.0');
        
            spr_hvb.get('localIdentifiers').get(2).put('value', hvbSerialNo);
        
            spr_hvb.put('catenaXId', hvbUuid);
            apr_hvb.put('catenaXId', spr_hvb.get('catenaXId'));
            // apr_vehicle.get('childParts').get(0).put('childCatenaXId', apr_hvb.get('catenaXId'));
            
            apr_hvb.get('childParts').get(0).get('quantity').put('quantityNumber', 1);
            
            var hvb_child = apr_hvb.get('childParts').get(0);
            apr_hvb.get('childParts').remove(0);
            
            var pd_hvb = scenario.generateTestData(ProductDescription);
            var mf_hvb = scenario.generateTestData(MaterialForRecycling);
            
            for(var j = 0; j < moduleQuantity; j++) {
                var cellModuleUuid = rand.getUuid();
                var cellModuleSerialNo = rand.getSerialNoNum(28);
            
                var spr_cellmodule = scenario.generateTestData(SerialPartTypization, 'spt_cellmodule', '1.0');
                var apr_cellmodule = scenario.generateTestData(AssemblyPartRelationship, 'apr', '1.0');
                
                spr_cellmodule.get('localIdentifiers').get(2).put('value', cellModuleSerialNo);
                
                spr_cellmodule.put('catenaXId', cellModuleUuid);
                apr_cellmodule.put('catenaXId', spr_cellmodule.get('catenaXId'));
                // apr_hvb.get('childParts').get(0).put('childCatenaXId', apr_cellmodule.get('catenaXId'));
                
                // apr_cellmodule.get('childParts').get(0).get('quantity').put('quantityNumber', 1);
                // apr_cellmodule.get('childParts').get(0).put('childCatenaXId', cellUuid);

                var hvb_child_j = cloneElement(hvb_child); // Object.assign({}, hvb_child);
                hvb_child_j.put('childCatenaXId', cellModuleUuid);
                apr_hvb.get('childParts').put(hvb_child_j);
                // apr_hvb.get('childParts').get(j).put('childCatenaXId', cellModuleUuid);
                
                
                var cellmodule_child = apr_cellmodule.get('childParts').get(0);
                apr_cellmodule.get('childParts').remove(0);
                
                var pd_cellmodule = scenario.generateTestData(ProductDescription);
                var mf_cellmodule = scenario.generateTestData(MaterialForRecycling);
                
                for(var k = 0; k < cellQuantity; k++) {
                    var cellUuid = rand.getUuid();
                    var cellModuleChild = cloneElement(cellmodule_child);
                    cellModuleChild.put('childCatenaXId', cellUuid);
                    apr_cellmodule.get('childParts').put(cellModuleChild);
                    
                    var pd_cell = scenario.generateTestData(ProductDescription);
                    var mf_cell = scenario.generateTestData(MaterialForRecycling);
                }
            }
        }
}
```

### Vehicle Blueprint
```javascript
// javascript 
// Name: OEMVehicleTemplate
// Version: 2.0

// This disables the auto-output of entity-grouped json-elements
// only the blueprint containers per part will be generated
scenario.setAutoAddTestdata(false);

// This enables the creation of a test-aas per part from a given aas template version
scenario.setAutoAddAas("aas", "1.0");

// Thist takes the actual vehicle blueprint template (name, version) and instanciates its content
scenario.generateFromVehicleTemplate("OEMVehicleTemplate", "2.0");


```