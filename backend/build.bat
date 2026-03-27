@echo off
title Mahathi Backend - Build
color 0A
echo.
echo  ********************************************
echo  *   Mahathi Backend - Build Script        *
echo  ********************************************
echo.

cd /d "%~dp0"

echo [1/3] Cleaning previous build...
call mvn clean -q

echo [2/3] Building project (skipping tests)...
call mvn package -DskipTests -q

if errorlevel 1 (
    echo.
    echo [ERROR] Build failed! Check errors above.
    pause
    exit /b 1
)

echo [3/3] Build successful!
echo.
echo  ********************************************
echo  *   Build Complete!                       *
echo  ********************************************
echo.
echo JAR Location: target\mahathi-contractor-api.jar
echo.
echo To run locally:
echo   mvn spring-boot:run
echo.
pause
