FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# give permission BEFORE using mvnw
RUN chmod +x mvnw

# build the project
RUN ./mvnw clean package -DskipTests

EXPOSE 8082

# run the generated jar
CMD ["java", "-jar", "target/projetSpringAvocat-0.0.1-SNAPSHOT.jar"]