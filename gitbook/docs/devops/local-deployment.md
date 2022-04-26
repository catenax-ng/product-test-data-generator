---
sidebar_position: 4
title: Local Deployment
---

To deploy the testdata-generator with all its components on your local machine you need to ensure the following steps:

## Prerequisites

* Working docker installation with standard permissions
* A few gb disc space left
* Internet connection to download docker images for tdg

## Hint
Always persist any scenarios and templates locally in case anything gets lost or your local docker or system encounters any errors!

## Installation Process

1. Create a local keycloak instance with the script under *'local-docker/keycloak.sh'.*

2. Edit the first lines of file *'deploy-local.sh'* according to the parameters of your local machine (hint: use ip instead of hostname, don't use 127.0.0.1)
```bash
#!/bin/bash

export repo="ghcr.io/catenax-ng/product-test-data-generator"
export hostname="<your main ip here>"
export realm="master"
export client="<client name for tdg goes here>"
export secret="<client secret goes here>"
```

3. Run script *'./deploy-local.sh'* to install the tdg completely local.
This might take a few minutes.
In some cases, docker will produce an error during the setup process (depending on your docker version). In that case, just run the script again and everything should work fine.

4. Your instance is ready now to use. You can reach the ui via port *8090* and the swagger interface via *8080*. The authentication api-key for the api is *SPEEDBOAT* if not changed in the script.

5. (Optional) if you want to import test scenarios and templates, please open a docker-shell into your db-pod and enter the following:
```bash
cd /db-template
./import.sh
```
In case this doesn't work, try the following:
```bash
cd /db-template

mongoimport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_data_scenario --file /db-template/test_data_scenario.json
mongoimport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_data_template --file /db-template/test_data_template.json
mongoimport -p "admin" -u "admin" --authenticationDatabase "admin" -d tdg --collection test_meta_model    --file /db-template/test_meta_model.json
```

6. If after a reboot the docker instances fail to start, please repeat step 3 to newly set the variables.