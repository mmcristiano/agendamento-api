FROM java:8
VOLUME /tmp
COPY target/agenda-0.0.1-SNAPSHOT.jar api.jar
EXPOSE 8060
ENTRYPOINT ["java","-jar","/api.jar"]