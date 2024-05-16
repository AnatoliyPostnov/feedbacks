FROM gradle:8.7-jdk17 AS TEMP_BUILD_IMAGE

ENV APP_HOME=/app/feedbacks
WORKDIR $APP_HOME
COPY . .

RUN gradle bootJar --refresh-dependencies

FROM openjdk:17.0-jdk-slim
ENV ARTIFACT_NAME=*.jar
ENV APP_HOME=/app/feedbacks

ENV JPAGENT_PATH=""
ENV JAVA_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:MaxRAMPercentage=70.0 -XX:+UseContainerSupport -XX:InitialRAMPercentage=40.0 -XX:MinRAMPercentage=20.0 -XX:+CrashOnOutOfMemoryError -XX:HeapDumpPath=/heapdumps/service%p.hprof"

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME ./app.jar
EXPOSE 8080

CMD java $JAVA_OPTS $JPAGENT_PATH -Dspring.profiles.active=prod -jar app.jar
