FROM gradle:jdk17-alpine as build-stage
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:17-alpine as production-stage
RUN apk add --no-cache tzdata
ENV TZ=Asia/Bangkok
RUN mkdir /app
COPY --from=build-stage /home/gradle/src/build/libs/api-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
