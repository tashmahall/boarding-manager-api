FROM maven:3.5.2-jdk-8-alpine AS build

RUN  mkdir /work

COPY  .  /work

WORKDIR /work

RUN mvn clean install 

FROM openjdk:8-jdk-alpine

EXPOSE 8080

RUN mkdir /work

COPY --from=build  /work/target/*.jar  /work/app.jar 

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/work/app.jar"]
