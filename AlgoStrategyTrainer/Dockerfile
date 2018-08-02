FROM openjdk:8-alpine AS build

WORKDIR /app

COPY . .

FROM openjdk:8-alpine

WORKDIR /app

COPY --from=build /app/target/AlgoStrategyTrainer-1.0-SNAPSHOT.jar .

CMD ["java", "$JAVAOPT", "-jar", "/app/AlgoStrategyTrainer-1.0-SNAPSHOT.jar"]
