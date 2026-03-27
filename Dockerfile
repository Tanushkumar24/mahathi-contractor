# ===========================================
# STAGE 1: Build
# ===========================================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy entire backend folder
COPY backend/ ./backend/

# Build the project
RUN mvn -f backend/pom.xml clean package -DskipTests -B

# ===========================================
# STAGE 2: Runtime
# ===========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy JAR from build stage
COPY --from=builder /app/backend/target/mahathi-contractor-api.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx512m", "-XX:+UseG1GC", "app.jar"]
