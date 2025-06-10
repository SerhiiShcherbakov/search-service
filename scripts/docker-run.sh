#!/bin/bash

docker run -ti --rm -p 8080:8080 \
  --network="host" \
  -e JAVA_TOOL_OPTIONS="-Dspring.profiles.active=local" \
  -e GATEWAY_KEY="test" \
  -e ELASTICSEARCH_URI="http://localhost:9200" \
  -e RABBITMQ_HOST="localhost" \
  -e RABBITMQ_PORT="5672" \
  -e RABBITMQ_USERNAME="myuser" \
  -e RABBITMQ_PASSWORD="secret" \
  --name="search-service" "search-service:1.0.0"