FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/consumptionservice-0.0.1-SNAPSHOT.jar consumptionservice.jar
COPY src/main/config/config.ini config/config.ini
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "consumptionservice.jar"]
