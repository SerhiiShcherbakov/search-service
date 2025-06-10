# Search Service

## Features
- Elasticsearch-based notebook search
- Filtering and sorting capabilities
- Tag-based search for notebooks
- Integration with RabbitMQ for messaging

## Technology Stack
- **Spring Boot**
- **Spring Data Elasticsearch**
- **RabbitMQ**
- **Swagger/OpenAPI**
- **Actuator**, **Prometheus**
- **Docker**

## Getting Started

Start the required infrastructure using Docker Compose
``` bash
docker compose up -d
```

Build
``` bash
./scripts/docker-build.sh
```

Run
``` bash
./scripts/docker-run.sh
```