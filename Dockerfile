FROM openjdk:17.0-jdk-slim
WORKDIR ./app/feedbacks
COPY /build/libs/feedbacks-0.0.1-SNAPSHOT.jar .
ENV JPAGENT_PATH=""
ENV JAVA_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:MaxRAMPercentage=70.0 -XX:+UseContainerSupport -XX:InitialRAMPercentage=40.0 -XX:MinRAMPercentage=20.0 -XX:+CrashOnOutOfMemoryError -XX:HeapDumpPath=/heapdumps/service%p.hprof"
EXPOSE 8080
CMD java $JAVA_OPTS $JPAGENT_PATH -jar feedbacks-0.0.1-SNAPSHOT.jar
