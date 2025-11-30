#!/bin/bash
set -e  # Detener ejecución si ocurre algún error

echo "1. Iniciando contenedor postgres-prod..."
docker compose up -d postgres-prod

echo "Esperando a que Postgres esté listo..."
until docker compose exec -T postgres-prod pg_isready -U isaii_postgre_admin > /dev/null 2>&1; do
    echo "Postgres no está listo aún..."
    sleep 2
done
echo "Postgres está listo."

echo "2. Construyendo api sin cache..."
docker compose build api --no-cache

echo "3. Levantando api..."
docker compose up api
