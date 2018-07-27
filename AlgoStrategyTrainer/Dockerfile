FROM tomcat:8.0.20-jre8 AS build

WORKDIR /app

COPY . .

FROM openjdk:8-alpine 

WORKDIR /app


#Install apache tomcat
RUN groupadd tomcat
RUN useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
RUN cd /tmp
RUN curl -O http://apache.mirrors.ionfish.org/tomcat/tomcat-    8/v8.5.12/bin/apache-tomcat-8.5.12.tar.gz
RUN mkdir /opt/tomcat
RUN tar xzvf apache-tomcat-8*tar.gz -C /opt/tomcat --strip-components=1
RUN cd /opt/tomcat
RUN chgrp -R tomcat /opt/tomcat
RUN chmod -R g+r /opt/tomcat/conf
RUN chmod g+x /opt/tomcat/conf
RUN chown -R tomcat /opt/tomcat/webapps /opt/tomcat/work /opt/tomcat/temp opt/tomcat/logs

Run cd /opt/tomcat/bin

COPY --from=build /app/target/Spring4Rest.war /usr/local/tomcat/webapps/Spring4Rest.war 

CMD [ "catalina.sh", "run"]

#ENTRYPOINT ["java", "-war", "/app/Spring4Rest.war"]
