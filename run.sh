#!/bin/bash
set -eo pipefail
shopt -s nullglob

echo "-----Starting Messagerie application!-----"

# use compose to start cassandra and redis
docker-compose up -D

# sleep in order to give time to services to start
echo "-----Waiting for services to start!-----"
sleep 30

# configure cassandra. TODO

# start application, TODO find a detached mode
docker run -p 8080:8080 -it messagerie:1
echo "-----Messagerie ready!-----"

