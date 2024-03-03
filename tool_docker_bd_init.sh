#!/usr/bin/env sh

docker exec -it mongo-greenscore bash -c "mongosh < /scripts/reinit_database.js"