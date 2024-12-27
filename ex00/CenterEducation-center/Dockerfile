# This Dockerfile uses a multi-stage build process:
#     First stage builds the application using Maven, producing a WAR file.
#     Second stage takes this WAR file and sets up a Tomcat server with the application deployed.

FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /CenterEducation-center
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM tomcat:10-jdk21
ENV CATALINA_OPTS="-Dspring.profiles.active=docker"
COPY --from=build /CenterEducation-center/target/CenterEducation-center-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/CenterEducation-center.war