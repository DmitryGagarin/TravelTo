# Use Eclipse Temurin instead of plain OpenJDK
FROM eclipse-temurin:17.0.11_9-jre-jammy

ARG JAR_FILE=target/travel-to-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Add these critical JVM options
ENTRYPOINT ["java", \
    "-Dmanagement.metrics.enable.process.cpu=false", \
    "-Dmanagement.metrics.binders.process.enabled=false", \
    "-Dio.micrometer.shaded.jcgroups.load.service=false", \
    "-jar", "/app.jar"]