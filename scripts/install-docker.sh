#!/usr/bin/env bash

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

sudo apt-get update

apt-cache policy docker-ce

sudo apt-get install -y docker-ce

sudo systemctl status docker

## add docker to sudo user
#sudo usermod -aG docker ${USER}

##https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04