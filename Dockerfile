FROM openjdk:8-jre

RUN mkdir /demo

COPY target/demo.jar /demo

CMD java  -jar /demo/demo.jar