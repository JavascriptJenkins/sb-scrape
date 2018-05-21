#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

sh 'ls'

sh 'mkdir mynewdir'

sh 'docker container ls'

sh '/bin/bash docker kill $(docker ps -q)'