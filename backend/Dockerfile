FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "target/mahathi-contractor-api.jar"]