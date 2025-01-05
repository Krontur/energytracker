@echo off
setlocal enabledelayedexpansion

:: Define los microservicios
set services=consumptionservice datacollector devicecatalog userservice

echo Starting build process for microservices...

:: Iterar sobre cada microservicio
for %%s in (%services%) do (
    echo Building %%s...
    if exist %%s (
        pushd %%s
        call .\mvnw clean package -DskipTests
        if errorlevel 1 (
            echo Failed to build %%s. Exiting...
            exit /b 1
        )
        popd
    ) else (
        echo Directory %%s does not exist. Skipping...
    )
)

echo Build process completed successfully!

:: Levantar los contenedores con Docker Compose
echo Starting Docker Compose...
docker-compose -f .\compose.prod.yaml up --build -d

:: Verificar si Docker Compose se ejecutó correctamente
if errorlevel 1 (
    echo Docker Compose failed to start. Exiting...
    exit /b 1
)

echo Docker Compose started successfully!
pause
