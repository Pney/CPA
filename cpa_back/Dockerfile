FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY src /app/src
COPY pom.xml /app
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar app/app.jar
WORKDIR /app
EXPOSE 8000
ENTRYPOINT ["java","-jar","app.jar"]

