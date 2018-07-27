FROM tomcat:8.0.20-jre8 AS build

WORKDIR /app

COPY . .

FROM openjdk:8-alpine 

WORKDIR /app

COPY --from=build /app/target/Spring4Rest.war /usr/local/tomcat/webapps/Spring4Rest.war 

#ENTRYPOINT ["java", "-war", "/app/Spring4Rest.war"]
