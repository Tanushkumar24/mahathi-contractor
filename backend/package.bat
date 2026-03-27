@echo off
title Mahathi Backend - Package
color 0A
echo.
echo  ********************************************
echo  *   Mahathi Backend - Package Script      *
echo  ********************************************
echo.

cd /d "%~dp0"

echo Building JAR file...
echo.

call mvn package -DskipTests

if errorlevel 1 (
    echo.
    echo [ERROR] Package failed!
    pause
    exit /b 1
)

echo.
echo [SUCCESS] JAR file created!
echo.
dir target\*.jar
echo.
pause
