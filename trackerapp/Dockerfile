FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
ARG JAR_FILE=target/trackerapp-1.0.jar
COPY ${JAR_FILE} .
ENTRYPOINT ["java","-jar","trackerapp-1.0.jar"]