# Etapa de build
FROM --platform=linux/amd64 maven:3.9.9-amazoncorretto-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests

FROM --platform=linux/amd64 amazoncorretto:21.0.7-alpine

WORKDIR /app

COPY --from=builder /app/target/guest-*.jar guest.jar

EXPOSE 8080 80

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "guest.jar"]
