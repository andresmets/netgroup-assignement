FROM openjdk:24-ea-24-oracle
COPY netgroup-server/build/libs/netgroup-server-0.0.1-SNAPSHOT.jar /usr
WORKDIR /usr
RUN sh -c "touch netgroup-server-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["java", "-jar", "netgroup-server-0.0.1-SNAPSHOT.jar"]