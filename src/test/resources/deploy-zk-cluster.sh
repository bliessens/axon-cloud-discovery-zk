#!/usr/bin/env bash

docker network create --internal --driver bridge backbone

docker run -d --name zookeeper1 --network backbone -e ZOO_MY_ID=1 -e ZOO_SERVERS="server.1=0.0.0.0:2888:3888 server.2=zookeeper2:2888:3888 server.3=zookeeper3:2888:3888" zookeeper:3.4.13
docker run -d --name zookeeper2 --network backbone -e ZOO_MY_ID=2 -e ZOO_SERVERS="server.1=zookeeper1:2888:3888 server.2=0.0.0.0:2888:3888 server.3=zookeeper3:2888:3888" zookeeper:3.4.13
docker run -d --name zookeeper3 --network backbone -e ZOO_MY_ID=3 -e ZOO_SERVERS="server.1=zookeeper1:2888:3888 server.2=zookeeper2:2888:3888 server.3=0.0.0.0:2888:3888" zookeeper:3.4.13