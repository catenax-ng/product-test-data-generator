#! /bin/bash

helm template . -n product-test-data-generator --debug --dry-run --values values.yaml | less
