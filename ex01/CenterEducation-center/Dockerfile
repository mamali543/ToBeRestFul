FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:10-jdk21
ENV CATALINA_OPTS="-Dspring.profiles.active=docker"
# Ensure this WAR file name matches the output from your pom.xml in ex01
COPY --from=build /app/target/CenterEducation-center-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/CenterEducation-center.war