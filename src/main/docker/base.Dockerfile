# base.Dockerfile
FROM alpine:latest

# Install OpenJDK 17 and Maven
RUN apk update && apk add --no-cache openjdk17-jdk maven


# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH=$PATH:$JAVA_HOME/bin

WORKDIR /app
