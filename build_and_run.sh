#!/bin/bash

set -e  # Salir si ocurre algún error

# Microservicios a construir
services=("consumptionservice" "datacollector" "devicecatalog" "userservice")

echo "Starting build and setup process for microservices..."

# 1. Configurar permisos para mvnw
echo "Setting execute permissions for mvnw files..."
for service in "${services[@]}"; do
  if [ -f "$service/mvnw" ]; then
    chmod 755 "$service/mvnw"
    echo "Permissions set for $service/mvnw"
  else
    echo "mvnw not found in $service"
  fi
done

# Configurar permisos en la raíz para build_and_run.sh (si es necesario)
if [ -f "./build_and_run.sh" ]; then
  chmod 755 "./build_and_run.sh"
  echo "Permissions set for root/build_and_run.sh"
else
  echo "build_and_run.sh not found in root"
fi

# 2. Construir microservicios con Maven Wrapper
echo "Building microservices..."
for service in "${services[@]}"; do
  echo "Building $service..."
  if [ -d "$service" ]; then
    # Entrar al directorio del microservicio
    pushd "$service" > /dev/null
    ./mvnw clean package -DskipTests
    if [ $? -ne 0 ]; then
      echo "Failed to build $service. Exiting..."
      exit 1
    fi
    # Volver al directorio raíz
    popd > /dev/null
  else
    echo "Directory $service does not exist. Skipping..."
  fi
done

echo "Build process completed successfully!"

# 3. Levantar los contenedores con Docker Compose
echo "Starting Docker Compose..."
docker-compose -f ./compose.prod.yaml up --build -d
if [ $? -ne 0 ]; then
  echo "Docker Compose failed to start. Exiting..."
  exit 1
fi

echo "Docker Compose started successfully!"
