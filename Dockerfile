#Base image for metadata
FROM ubuntu:latest
LABEL authors="Matias_Araya"

#Build stage
FROM alpine:latest as build

RUN apk update
RUN apk add openjdk17

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

#Runtime stage
FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build /build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]