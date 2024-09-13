FROM openjdk:17-jre
COPY target/redis-kafka-app.jar /app/cacheapp.jar
ENTRYPOINT ["java", "-jar", "/app/cacheapp.jar"]
