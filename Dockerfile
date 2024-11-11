FROM maven:3.6-openjdk-17 AS builder

WORKDIR /cv_generator

COPY pom.xml /cv_generator

RUN mvn dependency:go-offline -B

COPY ./src /cv_generator/src

RUN mvn clean install -DskipTests

FROM openjdk:17-slim

WORKDIR /cv_generator

EXPOSE 8080

COPY --from=builder /cv_generator/target/*.jar /cv_generator/app.jar

ENTRYPOINT ["java", "-jar", "/cv_generator/app.jar"]