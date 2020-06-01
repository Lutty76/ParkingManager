FROM openjdk:11.0.7-slim
RUN groupadd -r spring && useradd --no-log-init -r -g spring spring
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
