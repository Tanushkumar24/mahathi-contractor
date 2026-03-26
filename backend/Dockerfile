<<<<<<< HEAD
FROM eclipse-temurin:17-jdk-jammy
=======
FROM openjdk:17-jammy
>>>>>>> 167d24273b5e7c5001018c83da9fe2f714ddf60c

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "target/mahathi-contractor-api.jar"]