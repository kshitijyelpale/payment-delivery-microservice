FROM openjdk:17-oracle
EXPOSE 8080
ARG JAR_FILE=target/delivery-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} delivery.jar
ENTRYPOINT ["java", "-jar", "/delivery.jar"]