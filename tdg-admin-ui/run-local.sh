#!/bin/bash

export TDG_HOST_SECURE=false
export TDG_HOST_NAME=192.168.1.224
export TDG_HOST_PORT=8090
export TDG_HOST_PORT_EXTERNAL=8090
export TDG_API_ENDPOINT=/api

export TDG_API_SECURE=false
export TDG_API_HOST_NAME=192.168.1.224
export TDG_API_HOST_PORT=8080
export TDG_API_KEY=SPEEDBOAT

export TDG_IAM_SERVER_URL=http://192.168.1.224:9001/auth
export TDG_IAM_RESOURCE=tdg-ui
export TDG_IAM_REALM=master
export TDG_IAM_SECRET=xpWj3Eb8r7Hs9OZIFR8XAVqFpwuExQvC

#mvn clean spring-boot:run -Dspring-boot.run.jvmArguments="-Xms2048m -Xmx4096m"
mvn clean package

java -jar target/*.jar

