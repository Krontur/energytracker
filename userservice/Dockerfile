FROM eclipse-temurin:17-jdk-jammy as builder
MAINTAINER "Oscar G. Tur"
COPY target/userservice.jar /app/userservice.jar
ENTRYPOINT ["java", "-jar", "/app/userservice.jar"]
