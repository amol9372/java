FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-service-1.0.jar
ENTRYPOINT ["java","-jar","user-service-1.0.jar"]