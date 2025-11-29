# ---------- Stage 1: Build ----------
FROM gradle:8.14-jdk21 AS build
WORKDIR /app

# Copy Gradle wrapper and build files first (for caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Download dependencies (speeds up rebuilds)
RUN ./gradlew build -x test --no-daemon || true

# Copy the rest of the project
COPY . .

# Build the fat jar
RUN ./gradlew bootJar --no-daemon

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from build stage (Gradle produces build/libs/*.jar)
COPY --from=build /app/build/libs/*.jar app.jar

# Expose your Spring Boot port
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]