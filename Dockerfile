# Dockerfile
FROM openjdk:11-jdk-slim
USER 1029
COPY target/graph-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

