#! /bin/bash

host="localhost"

mongoexport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_data_template --out test_data_template.json
mongoexport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_data_scenario --out test_data_scenario.json
mongoexport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_meta_model --out test_meta_model.json
