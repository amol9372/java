FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} auth-service-1.0.jar
ENTRYPOINT ["java","-jar","/auth-service-1.0.jar"]
