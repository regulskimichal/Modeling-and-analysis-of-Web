FROM adoptopenjdk/openjdk11:alpine-slim
COPY build/libs/*.jar /usr/app/app.jar
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
