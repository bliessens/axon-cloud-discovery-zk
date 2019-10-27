#!/usr/bin/env bash

ZK_VERSION=3.4.13
NETWORK=backbone

#docker network create --driver bridge $NETWORK

docker run -d --name zookeeper1 --net=host -e ZOO_MY_ID=1 -e ZOO_SERVERS="server.1=0.0.0.0:2888:3888 server.2=zookeeper2:2888:3888 server.3=zookeeper3:2888:3888" zookeeper:$ZK_VERSION
docker network connect $NETWORK zookeeper1
docker run -d --name zookeeper2 --net=host -e ZOO_MY_ID=2 -e ZOO_SERVERS="server.1=zookeeper1:2888:3888 server.2=0.0.0.0:2888:3888 server.3=zookeeper3:2888:3888" zookeeper:$ZK_VERSION
docker network connect $NETWORK zookeeper2
docker run -d --name zookeeper3 --net=host -e ZOO_MY_ID=3 -e ZOO_SERVERS="server.1=zookeeper1:2888:3888 server.2=zookeeper2:2888:3888 server.3=0.0.0.0:2888:3888" zookeeper:$ZK_VERSION
docker network connect $NETWORK zookeeper3

#EUREKA_IMAGE=netflixoss/eureka:1.3.1
#DISCOVERY_NAME=eureka
#docker run -d --name $DISCOVERY_NAME -p 8090:8080 $EUREKA_IMAGE
#docker network connect $NETWORK $DISCOVERY_NAME