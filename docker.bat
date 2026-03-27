@echo off
echo ==========================================
echo   Mahathi Contractor - Docker Commands
echo ==========================================
echo.

if "%1"=="" goto help
if "%1"=="start" goto start
if "%1"=="stop" goto stop
if "%1"=="restart" goto restart
if "%1"=="logs" goto logs
if "%1"=="build" goto build
if "%1"=="clean" goto clean
goto help

:start
echo Starting services...
docker-compose up -d
echo.
echo Services started!
echo Backend: http://localhost:8080
echo Health: http://localhost:8080/api/health
echo.
echo Run 'docker-compose logs -f' to see logs
goto end

:stop
echo Stopping services...
docker-compose down
echo Services stopped.
goto end

:restart
echo Restarting services...
docker-compose restart
echo Services restarted.
goto end

:logs
echo Showing logs (Ctrl+C to exit)...
docker-compose logs -f
goto end

:build
echo Building Docker images...
docker-compose build --no-cache
echo Build complete.
goto end

:clean
echo Cleaning up...
docker-compose down -v
docker system prune -f
echo Cleanup complete.
goto end

:help
echo ==========================================
echo   Available Commands
echo ==========================================
echo.
echo   docker.bat start    - Start all services
echo   docker.bat stop     - Stop all services
echo   docker.bat restart  - Restart all services
echo   docker.bat logs     - View logs
echo   docker.bat build    - Rebuild images
echo   docker.bat clean    - Stop and remove all data
echo.
echo ==========================================
echo   Services
echo ==========================================
echo.
echo   Backend API: http://localhost:8080
echo   MySQL: localhost:3306
echo.
echo   MySQL Credentials:
echo   - Host: localhost
echo   - Port: 3306
echo   - User: root
echo   - Password: mahathi123
echo   - Database: mahathi_contractor
echo.
goto end

:end
pause
