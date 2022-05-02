#! /bin/bash   

prefix="mongoimport --authenticationDatabase 'admin'"

mongoimport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_data_scenario --file /db-template/test_data_scenario_local.json 
mongoimport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_data_template --file /db-template/test_data_template_local.json
mongoimport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_meta_model    --file /db-template/test_meta_model_local.json

