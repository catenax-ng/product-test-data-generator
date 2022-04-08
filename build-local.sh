#!/bin/bash

export repo="ghcr.io/catenax/testdatagenerator"
export hostname="192.168.1.229"

cd tdg
pwd

mvn clean package

image="$repo/tdg:main"
docker build -f ./src/main/docker/Dockerfile -t $image .

cd ..
cd tdg-admin-ui
pwd

mvn clean package

image="$repo/tdg-admin-ui:main"
docker build -f ./src/main/docker/Dockerfile -t $image .

cd ..

cd local-docker
pwd

# Database parameters
export TDG_DB_HOSTNAME="db"
export TDG_DB_PORT="27017"
export TDG_DB_NAME="tdg"
export TDG_DB_USERNAME="admin"
export TDG_DB_PASSWORD="admin"

# API Server parameters
export TDG_HOST_SECURE="false"
export TDG_HOST_NAME="$hostname"
export TDG_HOST_PORT="8080"
export TDG_API_KEY="SPEEDBOAT"

# Admin parameters
export TDG_ADMIN_HOST_SECURE="false"
export TDG_ADMIN_HOST_NAME="$hostname"
export TDG_ADMIN_HOST_PORT="8090"

# Please install keycloak and set values here
export TDG_IAM_SERVER_URL=http://192.168.1.229:9001/auth
export TDG_IAM_RESOURCE=tdg-ui
export TDG_IAM_REALM=master
export TDG_IAM_SECRET=ca367b10-3090-475b-a415-2630941e3d93


docker-compose up # --build --force-recreate --renew-anon-volumes


cd ..



