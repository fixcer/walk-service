# Base image
FROM openjdk:17-jdk-slim

# Keep consistent with pom.xml
ENV APP_JAR_NAME walk-service

RUN mkdir /app

ADD ./target/walk-service.jar /app/

WORKDIR /app

# Expose port
EXPOSE 8181

ENTRYPOINT ["java", "-jar", "walk-service.jar"]
