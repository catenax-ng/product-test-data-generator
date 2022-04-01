// Name: jstest
// Version: 1.1

var BusinessPartner = scenario.getSchema('BPDM', '1.1.0');
// var bmw = scenario.generateTestData(BusinessPartner, 'bp_bmw', '1.0');

// var Traceability = scenario.getSchema('Traceability', '0.1.1');
var SerialPartTypization = scenario.getSchema('SerialPartTypization', '1.0.0');
var AssemblyPartRelationship = scenario.getSchema('AssemblyPartRelationship', '1.0.0');
// var Material = scenario.getSchema('Material', '0.1.1');
// var TechnicalData = scenario.getSchema('TechnicalData', '0.1.1');

var cellUuid = rand.getUuid();

var vehicleQuantity = 1;
var hvbQuantity = 1;
var moduleQuantity  = 6;
var cellQuantity    = 10;

for(var i = 0; i < vehicleQuantity; i++) {
        var spr_vehicle = scenario.generateTestData(SerialPartTypization, 'spt_vehicle', '1.0');

        var van = rand.getVan();
        spr_vehicle.get('localIdentifiers').get(2).put('value', van);
        spr_vehicle.get('localIdentifiers').get(3).put('value', van);

        var apr_vehicle = scenario.generateTestData(AssemblyPartRelationship, 'apr', '1.0');
        apr_vehicle.put('catenaXId', spr_vehicle.get('catenaXId'));

        for(var h = 0; h < hvbQuantity; h++) {
            var hvbUuid = 'hvb_' + rand.getUuid();
            
            var spr_hvb = scenario.generateTestData(SerialPartTypization, 'spt_hvb', '1.0');
            var apr_hvb = scenario.generateTestData(AssemblyPartRelationship, 'apr', '1.0');
        
            spr_hvb.put('catenaXId', hvbUuid);
            apr_hvb.put('catenaXId', spr_hvb.get('catenaXId'));
            // apr_vehicle.get('childParts').get(0).put('childCatenaXId', apr_hvb.get('catenaXId'));
            
            apr_hvb.get('childParts').get(0).get('quantity').put('quantityNumber', 1);
            
            var hvb_child = apr_hvb.get('childParts').get(0);
            apr_hvb.get('childParts').remove(0);
            
            for(var j = 0; j < moduleQuantity; j++) {
                var cellModuleUuid = 'cellmodule_' + rand.getUuid();
            
                var spr_cellmodule = scenario.generateTestData(SerialPartTypization, 'spt_cellmodule', '1.0');
                var apr_cellmodule = scenario.generateTestData(AssemblyPartRelationship, 'apr', '1.0');
                
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
                
                for(var k = 0; k < cellQuantity; k++) {
                    
                }
            }
        }
}



