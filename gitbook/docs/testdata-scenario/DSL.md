---
sidebar_position: 2
title: Domain Specific Language (DSL)
---
## Summary
This method allows for a simple, no/low-code variation of instanciation of mostly synthetic or manually enriched test data.

It is implemented via antlr vor domain specific languages. For the language definition, please see [here](https://github.com/catenax-ng/product-test-data-generator/blob/main/tdg/src/main/antlr4/com/catenax/tdm/scenario/TestDataScenario.g4).
The source of this language is within the domain of this product and can therefore be changed according to future needs and requirements.

## Examples
Simple example to instanciate 5 Traceability items, completely synthetical according to the defined schema:
```text
Name: UC_Quality;
Version: 1.0;

Entities:
    Entity:
        Name: Traceability
        Version: 0.1.1
        As: Traceability;
        
Generate Traceability times 5 As: t;
```

More complex example to instanciate and manipulate items:
```text
Name: Traceability;
Version: 1.3;

Entities:
    Entity:
        Name: BusinessPartner
        Version: 0.0.1
        As: BusinessPartner;
    Entity:
        Name: Traceability
        Version: 0.1.1
        As: Traceability;
    Entity:
        Name: Material
        Version: 0.1.1
        As: Material;
    Entity:
        Name: TechnicalData
        Version: 0.1.1
        As: TechnicalData;
        
Instance BusinessPartner As: bmwgroup;
Instance BusinessPartner As: bmwplant1;
Instance BusinessPartner As: bmwplant2;

Set bmwgroup.bpn = "CXBPNBMWGROUP";
Set bmwgroup.name = "BMW Group";
Set bmwgroup.parent = "";

Set bmwplant1.bpn = "CXBPNBMWDGF";
Set bmwplant1.name = "BMW Plant Dingolfing";
Set bmwplant1.parent = bmwgroup.bpn;

Set bmwplant2.bpn = "CXBPNBMWLPZ";
Set bmwplant2.name = "BMW Plant Leipzig";
Set bmwplant2.parent = bmwgroup.bpn;

Generate Traceability times 5 As: ts;

For Each ts As: t 
Do;
    Instance Material As: m;
    Set m.materialName = "Copper";
    
    Instance TechnicalData As: td;
    
    
    Set t.qualityAlertData = null;
    
    Var id = t.individualData;
    Set id.productionCountryCode = "DE";
    Set id.productionDateGMT = Random Date;
    Set t.individualData = id;

    Var ud = t.uniqueData;
    Set ud.customerUniqueID = bmwgroup.bpn;
    Set ud.manufacturerUniqueID = bmwplant1.bpn;
    Set ud.uniqueID = Random Number between 10000 and 60000;
    Set t.uniqueData = ud;
Done;
```


