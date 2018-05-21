#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

docker container 'ls'

docker kill sb-scrape

echo 'WAITING 5 seconds while sb-scape is killed!'

sleep 5s

docker run -d --name sb-scrape javascriptjenkins/sb-scrape:latest


