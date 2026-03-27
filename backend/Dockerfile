FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get install -y maven
RUN mvn -f backend/pom.xml clean install

CMD ["java", "-jar", "backend/target/mahathi-contractor-api.jar"]