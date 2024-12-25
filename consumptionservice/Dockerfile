FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/consumptionservice-0.0.1-SNAPSHOT.jar consumptionservice.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "consumptionservice.jar"]
