# ===========================================
# STAGE 1: Build
# ===========================================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY backend/pom.xml backend/
RUN mvn -f backend/pom.xml dependency:go-offline -B

# Copy source and build
COPY backend/src backend/
RUN mvn -f backend/pom.xml package -DskipTests -B

# ===========================================
# STAGE 2: Runtime
# ===========================================
FROM eclipse-temurin:17-jre

# Create non-root user for security
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR /app

# Copy JAR from build stage
COPY --from=builder /app/backend/target/mahathi-contractor-api.jar app.jar

# Set ownership
RUN chown -R appuser:appgroup /app

USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx512m", "-XX:+UseG1GC", "app.jar"]
