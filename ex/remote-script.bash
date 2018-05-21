#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

mkdir 'mynewdir'

docker container 'ls'

docker container 'kill $(docker ps -q)'