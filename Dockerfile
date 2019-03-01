# Start with a base image containing Java runtime
FROM openjdk:11-jdk

# Add Maintainer Info
LABEL maintainer="rururu0729@gmail.com"

ARG VERSION=0.0.1

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080
EXPOSE 80

# The application's jar file
ARG JAR_FILE=build/libs/api-${VERSION}.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]