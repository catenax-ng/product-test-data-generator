package com.catenax.tdm.util;

import java.util.ArrayList;

public class BuildDigestHashMap {

    public static void main(String[] args) {
        String[] s = {
                "https//catenax.io/schema/AAS/3.0",
                "https//catenax.io/schema/VehicleBlueprint/1.0.0",
                "https//catenax.io/schema/AssemblyPartRelationship/1.0.0",
                "https//catenax.io/schema/BPDM/1.1.0",
                "https//catenax.io/schema/BatteryProductDescription/1.0.1",
                "https//catenax.io/schema/MaterialForRecycling/1.0.0",
                "https//catenax.io/schema/PhysicalDimension/1.0.0",
                "https//catenax.io/schema/SerialPartTypization/1.0.0",
                "https//catenax.io/schema/TestDataContainer/1.0.0"
        } ;

        ArrayList<String> res = new ArrayList<String>();

        for (int i = 0; i < s.length; i++) {
            String uuid = DigestUtils.uuidFromHash(s[i]);
            res.add(uuid);
            String out = s[i] + " , " + uuid;
            System.out.println(out);
        }
        /**
         Current Mapping Table
         https//catenax.io/schema/AAS/3.0 , urn:uuid:60841e1-c5e8-7b08-203a-792b1a75
         https//catenax.io/schema/VehicleBlueprint/1.0.0 , urn:uuid:9495ee1-dbf4-8320-a2e3-82944c5b
         https//catenax.io/schema/AssemblyPartRelationship/1.0.0 , urn:uuid:b68bd52-cccf-3c3a-ad45-d62b59c2
         https//catenax.io/schema/BPDM/1.1.0 , urn:uuid:bb5c5f2-89b1-e1bc-101e-1f1f8db6
         https//catenax.io/schema/BatteryProductDescription/1.0.1 , urn:uuid:ddb2297-0800-862f-c950-9259c7c7
         https//catenax.io/schema/MaterialForRecycling/1.0.0 , urn:uuid:6829382-4c79-e6ae-9657-bd101337
         https//catenax.io/schema/PhysicalDimension/1.0.0 , urn:uuid:64e3741-4fb9-cc3f-dee5-aaf0f9cd
         https//catenax.io/schema/SerialPartTypization/1.0.0 , urn:uuid:ddc0cb6-116d-d330-2787-f461209c
         https//catenax.io/schema/TestDataContainer/1.0.0 , urn:uuid:dc6543d-ef97-bf87-c877-5a7e8e1a
        */
    }






}
