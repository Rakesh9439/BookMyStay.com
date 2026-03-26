FROM openjdk:17
WORKDIR /app
COPY target/service-registery.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]