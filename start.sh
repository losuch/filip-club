#!/bin/sh

set -e

echo "load env file"
source /app/app.env
# echo "run db migration"
# /app/migrate -path /app/migration -database "$DB_SOURCE" -verbose up

echo "start the app"
exec "$@"
