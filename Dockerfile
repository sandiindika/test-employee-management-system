FROM openjdk:17-jdk-slim

WORKDIR /ems-app

COPY target/ems-app-0.0.1-SNAPSHOT.jar /ems-app/ems-app.jar

ENV APP_PORT=${APP_PORT}
ENV DB_URL=${DB_URL}
ENV DB_PORT=${DB_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}

ENTRYPOINT ["java", "-jar", "ems-app.jar"]
