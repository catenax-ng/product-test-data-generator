#! /bin/bash

rm -rf .docusaurus

npm ci
npm run build

npm start
