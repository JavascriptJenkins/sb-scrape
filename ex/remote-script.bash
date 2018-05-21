#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

ls

docker 'container ls'

docker 'kill $(docker ps -q)'