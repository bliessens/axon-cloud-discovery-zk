#!/usr/bin/env bash

GROUP=$(grep group gradle.properties | awk -F '=' '{ print $2 }' | sed -e 's/[[:blank:]]*//g')
NAME=$(grep rootProject.name settings.gradle | tr "\"'" " " | awk -F '=' '{ print $2 }' | sed -e 's/[[:blank:]]*//g')
VERSION=$(grep version gradle.properties | awk -F '=' '{ print $2 }' | sed -e 's/[[:blank:]]*//g')

IMAGE_ID=$GROUP/$NAME:$VERSION

./gradlew buildDocker

#pushd .
#cd build/libs
#IMAGE_ID=$(docker build -t be.cheops.cloud/axon-cloud-discovery:0.0.1-SNAPSHOT -f ../../src/Dockerfile)
#echo "Built image $IMAGE_ID"
#popd

CID1=$(docker run -d --name axon-cloud1 --rm -p 9080:8080 -p 5990:5990 $IMAGE_ID)
docker network connect backbone $CID1
CID2=$(docker run -d --name axon-cloud2 --rm -p 9081:8080 -p 5991:5990 $IMAGE_ID)
docker network connect backbone $CID2

