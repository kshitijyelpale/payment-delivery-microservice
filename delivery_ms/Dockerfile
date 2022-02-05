FROM openjdk:17-oracle
EXPOSE 8080
ARG JAR_FILE=delivery-microservice.jar
ADD ${JAR_FILE} delivery.jar
ENTRYPOINT ["java", "-jar", "/delivery.jar"]