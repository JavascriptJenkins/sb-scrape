#!/usr/bin/env bash


ehco 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

ls

docker 'container ls'

docker 'kill $(docker ps -q)'