#!/bin/bash

docker stop iam
docker rm iam

docker run --name iam \
	-p 9001:8080 \
	--restart always \
	-e KEYCLOAK_USER=admin \
	-e KEYCLOAK_PASSWORD=admin \
	-d jboss/keycloak
