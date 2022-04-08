1. Define the variables in build-local.sh according to your environment and setup keycloak if required (see below) 
2. Run ./build-local.sh in top-level folder.
3. After instances have started on docker, cmd into db container and execute /db-template/import.sh to fill database with test content


Setup simple keycloak with docker: modify keycloak.sh according to your environment and run the script, run and configure your keycloak user and client as you wish and modify build-local.sh (top-level) accordingly

Prerequisites for build: 
JDK >= 12, 
Maven (current version), 
Docker, 
Bash / Git-Bash (Windows)
