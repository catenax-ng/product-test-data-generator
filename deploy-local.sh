#!/bin/bash

export repo="ghcr.io/catenax-ng/product-test-data-generator"
export hostname="192.168.1.224"
export realm="master"
export client="tdg-ui"
export secret="xpWj3Eb8r7Hs9OZIFR8XAVqFpwuExQvC"

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
export TDG_IAM_SERVER_URL=http://${hostname}:9001/auth
export TDG_IAM_RESOURCE=${client}
export TDG_IAM_REALM=${realm}
export TDG_IAM_SECRET=${secret}

cd local-docker

docker-compose up -d # --build --force-recreate --renew-anon-volumes

cd ..
