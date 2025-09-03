# 1) Build stage
FROM gradle:8.10.0-jdk21 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle --no-daemon clean bootJar

# 2) Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]