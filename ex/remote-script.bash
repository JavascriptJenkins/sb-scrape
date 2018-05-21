#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

sh '/bin/bash docker container ls'

sh '/bin/bash docker kill $(docker ps -q)'