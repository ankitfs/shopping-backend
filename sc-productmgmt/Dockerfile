FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/productmgmtapi.jar productmgmtapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "productmgmtapi.jar"]