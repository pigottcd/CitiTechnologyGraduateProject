FROM openjdk:8-alpine AS build

WORKDIR /APP

COPY . .

FROM openjdk:8-alpine 

WORKDIR /app

COPY --from=build /app/target/AlgoStrategyTrainer-1.0-SNAPSHOT.jar . 

ENTRYPOINT ["java", "-jar", "/app/AlgoStrategyTrainer-1.0-SNAPSHOT.jar"]
