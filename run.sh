#!/bin/bash
set -eo pipefail
shopt -s nullglob

echo "-----Starting Messagerie application!-----"

# use compose to start cassandra and redis
docker-compose up -d

# sleep in order to give time to services to start
echo "-----Waiting for services to start!-----"
sleep 30

# configure cassandra.
docker cp src/main/resources/statements.sql cassandra-host:/home/
docker exec -it cassandra-host cqlsh -f /home/statements.sql

# start application
docker run -p 8080:8080 --detach -t --network=messagerie_internal-network messagerie:1 
echo "-----Messagerie ready!-----"

