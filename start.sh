#!/bin/sh

set -e

echo "load env file"
source /app/app.env


echo "start the app"
exec "$@"
