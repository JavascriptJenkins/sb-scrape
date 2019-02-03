#!/usr/bin/env bash

## install on ubuntu 16.04
## https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-redis-on-ubuntu-16-04

sudo apt-get update

sudo apt-get install build-essential tcl

cd /tmp

curl -O http://download.redis.io/redis-stable.tar.gz

tar xzvf redis-stable.tar.gz

cd redis-stable

make

make test

sudo make install

sudo mkdir /etc/redis

sudo cp /tmp/redis-stable/redis.conf /etc/redis

sudo nano /etc/redis/redis.conf

sudo nano /etc/systemd/system/redis.service


sudo adduser --system --group --no-create-home redis

sudo mkdir /var/lib/redis

sudo chown redis:redis /var/lib/redis

sudo chmod 770 /var/lib/redis



redis-cli

 --> ping



sudo systemctl restart redis


## start up at startup
## sudo systemctl enable redis
