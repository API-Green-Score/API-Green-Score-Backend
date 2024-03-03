#!/usr/bin/env sh

echo "Stopping mongo APIGREEENSCORE database ..."
docker container stop mongo-greenscore
echo "Stopping mongo APIGREEENSCORE database ... [ OK ]"

echo "Deleting Docker container ..."
docker container rm mongo-greenscore
echo "Deleting Docker container ... [ OK ]"