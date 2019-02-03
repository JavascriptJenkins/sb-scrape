#!/usr/bin/env bash

## this will pull the mysql docker container that will work with this application
docker run --name nhl-container -e MYSQL_ROOT_PASSWORD=my-secret-pw -p 3306:3306 -d mysql:5.7.22

## this will let you connect to docker container and see logs
docker exec -it sports-five-container bash

## this will set JAVA_HOME on macs...
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home