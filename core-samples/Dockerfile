# syntax=docker/dockerfile:1
# core-samples（純 JDK / Preview 有効）。ビルドコンテキストはリポジトリのルート。

FROM gradle:8.10.2-jdk21 AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle :core-samples:jar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /home/gradle/project/core-samples/build/libs/core-samples.jar app.jar
EXPOSE 8080
ENV PORT=8080
# Preview 機能を含むため実行時も --enable-preview が必要
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]
