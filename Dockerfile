FROM openjdk:21-jdk-slim
WORKDIR /app
COPY applications/app-service/build/libs/crediyarequest.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]