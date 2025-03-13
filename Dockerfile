FROM openjdk:17-jdk-slim

COPY target/auth-microservice-0.0.1-SNAPSHOT.jar auth-microservice.jar
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "auth-microservice.jar"]
