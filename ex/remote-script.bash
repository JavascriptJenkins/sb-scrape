#!/usr/bin/env bash


echo 'OMG LOOK A REMOTE SCRIPT IS EXECUTING'

sh 'docker kill $(docker ps -q)'