#! /bin/bash

helm template chart/testdatagenerator/ --debug --dry-run --values chart/testdatagenerator/values-int.yaml