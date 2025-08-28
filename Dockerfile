FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/*.war app.war

ENTRYPOINT ["java","-jar","app.war"]
