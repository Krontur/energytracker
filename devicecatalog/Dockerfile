FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/devicecatalog-0.0.1-SNAPSHOT.jar devicecatalog.jar
COPY src/main/config/config.ini config/config.ini
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "devicecatalog.jar"]
