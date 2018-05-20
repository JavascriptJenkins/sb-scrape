FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY app.jar /app/ 
RUN chmod -R ag+w /app/ 

EXPOSE 8080
USER 1001

CMD java -jar /app/app.jar