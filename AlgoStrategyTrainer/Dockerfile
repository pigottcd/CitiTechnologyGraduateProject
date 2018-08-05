FROM openjdk:8-alpine AS build

WORKDIR /app

COPY . .

FROM openjdk:8-alpine

WORKDIR /app

COPY --from=build /app/target/AlgoStrategyTrainer-1.0-SNAPSHOT.jar .

EXPOSE 8082

CMD ["/opt/itrs/netprobe/nettprobe.linux_64", "$APPNAME", "-port", "7036", "-nopassword", "&"]

ENTRYPOINT java ${JAVAOPT} -jar /app/AlgoStrategyTrainer-1.0-SNAPSHOT.jar

ADD target/AlgoStrategyTrainer-1.0-SNAPSHOT.jar /usr/share/goldfish/runner.jar
