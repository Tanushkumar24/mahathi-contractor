@echo off
echo.
echo ========================================
echo   Mahathi Building Contractors Backend
echo ========================================
echo.

cd /d "%~dp0"

echo Checking prerequisites...
echo.

:: Check Java
java -version 2>&1 | findstr "version"
if errorlevel 1 (
    echo ERROR: Java not found!
    echo Please install Java 17+
    pause
    exit /b 1
)

echo.
echo Java OK
echo.

:: Check Maven
mvn -version 2>&1 | findstr "Apache"
if errorlevel 1 (
    echo ERROR: Maven not found!
    echo Please install Maven
    pause
    exit /b 1
)

echo Maven OK
echo.
echo Starting server at http://localhost:8080
echo Press Ctrl+C to stop
echo.
echo ========================================
echo.

mvn spring-boot:run
