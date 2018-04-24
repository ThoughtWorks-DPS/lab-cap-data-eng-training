FROM openjdk:8-jdk-alpine
ENV KAFKA_BROKERS docker.for.mac.localhost:9092
VOLUME /tmp
#ARG JAR_FILE
ADD bikestream-lastest.jar app.jar
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar