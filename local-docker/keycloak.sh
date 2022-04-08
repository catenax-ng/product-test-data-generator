#!/bin/bash

docker stop iam
docker rm iam

docker run --name iam \
	-p 9001:8080 \
	--restart always \
	-e KEYCLOAK_USER=admin \
	-e KEYCLOAK_PASSWORD=admin \
	-e POSTGRES_DB_ADDR=192.168.1.229 \
	-e POSTGRES_DB_PORT=5432 \
	-e POSTGRES_DB_DATABASE=iam \
	-e POSTGRES_DB_USER=admin \
	-e POSTGRES_DB_PASSWORD=admin \
	-d jboss/keycloak
