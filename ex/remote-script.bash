#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

sh 'docker container ls'

sh 'docker kill $(docker ps -q)'