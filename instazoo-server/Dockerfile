FROM maven:3.6.0-jdk-11-slim as build

RUN mkdir /instazoo
WORKDIR /instazoo
COPY pom.xml .
COPY ./src src
RUN mvn -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B clean verify

FROM openjdk:11-slim

WORKDIR /instazoo
COPY --from=build /dom/target/instazoo-server.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=image:/dev/./urandom","-jar", "-Dspring.profiles.active=default", "/dom/app.jar"]
