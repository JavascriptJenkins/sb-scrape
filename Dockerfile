FROM openjdk:8-jdk-alpine

COPY /target/app.jar /app/
RUN chmod -R ag+w /app/ 

EXPOSE 8080
USER 1001

CMD java -jar /app/app.jar