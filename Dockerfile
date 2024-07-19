FROM openjdk:17-jdk-slim
COPY WeiTalkBackEnd-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
