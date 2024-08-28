# backend/Dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "build/libs/backend-0.1-all.jar"]
