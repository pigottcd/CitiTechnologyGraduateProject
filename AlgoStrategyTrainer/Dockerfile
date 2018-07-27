FROM openjdk:8-alpine AS build

WORKDIR /app

COPY . .

FROM  tomcat

COPY  /app/target/Spring4Rest.war /usr/local/tomcat/webapps/Spring4Rest.war 

CMD [ "catalina.sh", "run"]

#ENTRYPOINT ["java", "-war", "/app/Spring4Rest.war"]
