#!/bin/bash

# Microservicios a construir
services=("consumptionservice" "datacollector" "devicecatalog" "userservice")

echo "Starting build process for microservices..."

# Iterar sobre cada microservicio y ejecutar el comando de Maven Wrapper
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

# Levantar los contenedores con Docker Compose
echo "Starting Docker Compose..."
docker-compose -f ./compose.prod.yaml up --build

# Verificar si Docker Compose se ejecutó correctamente
if [ $? -ne 0 ]; then
  echo "Docker Compose failed to start. Exiting..."
  exit 1
fi

echo "Docker Compose started successfully!"
