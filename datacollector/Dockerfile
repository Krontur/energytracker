FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/datacollector-0.0.1-SNAPSHOT.jar datacollector.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "datacollector.jar"]
