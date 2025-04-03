FROM gradle:8.11.1-jdk21 AS build

WORKDIR /app

COPY . /app

RUN chmod +x ./gradlew

RUN ./gradlew build

FROM openjdk:21-slim

WORKDIR /app

COPY --from=build /app/build/libs/*SNAPSHOT.jar app.jar

COPY src/main/resources/keystore.p12 /app/resources/keystore.p12

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
