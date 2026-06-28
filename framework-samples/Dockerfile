# syntax=docker/dockerfile:1
# framework-samples（Spring Boot 3 + Hibernate + H2）。ビルドコンテキストはリポジトリのルート。

FROM gradle:8.10.2-jdk21 AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle :framework-samples:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /home/gradle/project/framework-samples/build/libs/framework-samples.jar app.jar
EXPOSE 8081
# こちらは Preview 機能を使わないため --enable-preview は不要
ENTRYPOINT ["java", "-jar", "app.jar"]
