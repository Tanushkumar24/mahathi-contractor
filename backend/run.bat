@echo off
echo ========================================
echo   Mahathi Building Contractors - Backend
echo ========================================
echo.

cd /d "%~dp0"

echo Starting Spring Boot application...
echo Server will run at: http://localhost:8080
echo API Health Check: http://localhost:8080/api/health
echo.
echo Press Ctrl+C to stop the server
echo.

mvn spring-boot:run

pause
