FROM gradle:7.6-jdk8 AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
#RUN ./gradlew build -x test

# FROM openjdk:8-jdk-alpine
FROM eclipse-temurin:8-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=local"]