@echo off
echo ========================================
echo   Building Mahathi Backend
echo ========================================
echo.

cd /d "%~dp0"

echo Cleaning and building project...
call mvn clean package -DskipTests

if %errorlevel% equ 0 (
    echo.
    echo Build successful!
    echo JAR file created in target folder
    echo.
    echo To run the application:
    echo   1. Double-click 'run.bat' OR
    echo   2. Run: java -jar target\contractor-backend-1.0.0.jar
    echo.
) else (
    echo.
    echo Build failed. Please check the errors above.
    echo.
)

pause
