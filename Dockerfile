# Etapa 1: compilar con Maven (imagen grande, con JDK)
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline
COPY src src
RUN ./mvnw -q package -DskipTests

# Etapa 2: imagen final liviana (solo JRE + el .war)
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]
