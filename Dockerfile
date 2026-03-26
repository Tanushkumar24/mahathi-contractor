  FROM openjdk:17-jdk-slim

WORKDIR /app

COPY backend /app

RUN apt-get update && apt-get install -y maven
RUN mvn clean install

CMD ["java", "-jar", "target/mahathi-contractor-api.jar"]