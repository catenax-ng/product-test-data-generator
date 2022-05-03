#!/bin/bash

export TDG_HOST_SECURE=false
export TDG_HOST_NAME=localhost
export TDG_HOST_PORT=8080
export TDG_API_KEY=SPEEDBOAT

export TDG_DB_URI=mongodb://admin:admin@localhost/tdg
export TDG_DB_NAME=tdg
export TDG_DB_HOSTNAME=localhost
export TDG_DB_PORT=27017
export TDG_DB_USERNAME=admin
export TDG_DB_PASSWORD=admin

mvn clean package spring-boot:run -Dspring-boot.run.jvmArguments="-Xms2048m -Xmx4096m -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 "

