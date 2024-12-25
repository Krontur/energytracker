FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/userservice-0.0.1-SNAPSHOT.jar userservice.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "userservice.jar"]
