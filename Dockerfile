FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

LABEL authors="kubrafelek"

FROM openjdk:17.0.1-jdk-slim
COPY --from=build target/blog-app-0.0.1-SNAPSHOT.jar blog-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "blog-app.jar"]