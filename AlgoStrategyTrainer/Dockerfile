FROM openjdk:8-alpine AS build

WORKDIR /APP

COPY . .

FROM openjdk:8-alpine 

WORKDIR /app

COPY --from=build /app/target/Spring4Rest.war . 

ENTRYPOINT ["java", "-war", "/app/Spring4Rest.war"]
