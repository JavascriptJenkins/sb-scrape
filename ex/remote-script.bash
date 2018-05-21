#!/usr/bin/env bash


### Assumptions:
# 1) The docker user will be logged in on remote server where this is executing.
#
#

echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

docker container 'ls'

docker kill sb-scrape
echo 'WAITING 2 seconds while sb-scape is killed!'
sleep 2s

docker container rm sb-scrape
echo 'WAITING 2 seconds while sb-scape container name is removed!'
sleep 2s

docker pull javascriptjenkins/sb-scrape:latest
echo 'WAITING 2 seconds while sb-scape :latest is pulled for new changes!'
sleep 2s

echo 'Running new detached instance of container!'
docker run -d --name sb-scrape javascriptjenkins/sb-scrape:latest


