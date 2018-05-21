#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

docker container 'ls'

docker run 'javascriptjenkins/sb-scrape:latest'

