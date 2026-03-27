# ===========================================
# Local Development Startup Script
# ===========================================

echo "=========================================="
echo "  Mahathi Contractor - Local Setup"
echo "=========================================="
echo.

# Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed!
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

# Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven is not installed!
    echo Please install Maven 3.6+
    pause
    exit /b 1
)

# Check if MySQL is running
net start | findstr "MySQL" >nul 2>&1
if errorlevel 1 (
    echo WARNING: MySQL service may not be running.
    echo Please start MySQL before continuing.
    echo.
)

echo "Starting Mahathi Backend..."
echo.

cd /d "%~dp0\backend"

echo "Building project..."
call mvn clean compile -q

echo.
echo "Starting Spring Boot application..."
echo "=========================================="
echo.
echo "API will be available at: http://localhost:8080"
echo "Health Check: http://localhost:8080/api/health"
echo "Submissions: http://localhost:8080/api/submissions"
echo.
echo "Press Ctrl+C to stop the server"
echo "=========================================="
echo.

call mvn spring-boot:run

pause
