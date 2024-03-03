#!/usr/bin/env sh

echo "Launching initialization of mongo APIGREEENSCORE database ..."

echo "   Checking mount directory ..."
MOUNT_DIR_DATA=
if [ $# -eq 1 ]; then
  echo "Using specific mount directory: $1"
  MOUNT_DIR_DATA="-v $1:/data/db"
else
  echo "Using NO specific mount directory"
fi
echo "   Checking mount directory ... [ OK ]"

echo "   Creating Docker container ..."
docker run --name mongo-greenscore $MOUNT_DIR_DATA -d -p 27017:27017 mongo:7.0
echo "   Creating Docker container ... [ OK ]"

echo "Launching initialization of mongo APIGREEENSCORE database ... [ OK ]"