FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY sods-application/target/sods-application-1.0-SNAPSHOT.jar .
RUN sh -c 'touch sods-application-1.0-SNAPSHOT.jar'
EXPOSE 8888
ENTRYPOINT ["java","-Dspring.profiles.active=aws","-jar","sods-application-1.0-SNAPSHOT.jar"]