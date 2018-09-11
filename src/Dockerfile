FROM java:8-alpine
VOLUME /tmp

WORKDIR /opt/axon-cloud-discovery
ENV WORKDIR=/opt/axon-cloud-discovery

ENV JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5990
ENV ENVIRONMENT_VARIABLES=-Dspring.profiles.active=docker

#setting timezone
ENV TZ=Europe/Amsterdam
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD axon-cloud-discovery.jar axon-cloud-discovery.jar

EXPOSE 8080
EXPOSE 5990

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar $ENVIRONMENT_VARIABLES axon-cloud-discovery.jar

