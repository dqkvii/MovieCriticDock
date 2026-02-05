FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
WORKDIR /app

COPY --from=build /app/target/*.jar /app/
RUN ls -1 /app/*.jar | grep -v "original-" | head -n 1 | xargs -I{} cp "{}" /app/filmcritic.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/filmcritic.jar"]