# User.Dockerfile
FROM base:latest

WORKDIR /app/user


# Copy the JAR file to the container
COPY target/UserModule.jar /app/user/UserModule.jar

# Execute the application
CMD ["java", "-jar", "/app/user/UserModule.jar"]
