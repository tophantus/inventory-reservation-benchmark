# AGENTS.md

## Project

This repository contains:

* `inventory-reservation-benchmark/` — Spring Boot backend
* `infrastructure/` — Docker Compose, Prometheus, Grafana
* `benchmark/` — k6 benchmark scripts

## General Rules

* Keep business logic inside `inventory-reservation-benchmark/`.
* Do not modify reservation strategy behavior unless explicitly requested.
* Keep infrastructure configuration separate from application business logic.
* Use environment variables for environment-specific configuration.
* Never commit passwords, API keys, tokens, or other secrets.
* Prefer Docker Compose service names for container-to-container communication.
* Avoid unnecessary services, dependencies, abstractions, and configuration.
* Do not modify unrelated files.

## Directory Rules

### `inventory-reservation-benchmark/`

Follow `inventory-reservation-benchmark/AGENTS.md` for:

* Java/Spring Boot development
* Modular Monolith
* Hexagonal Architecture
* CQRS
* Domain/application/adapter boundaries
* Persistence
* Testing

### `infrastructure/`

Contains infrastructure configuration such as:

* Docker Compose
* Prometheus
* Grafana

Do not put application business logic or secrets here.

### `benchmark/`

Contains benchmark tooling such as k6 scripts.

Benchmark scripts must remain independent from application business logic.

## Validation

When changing infrastructure configuration, validate with:

```bash
docker compose config
```

When practical, verify affected services using Docker Compose before finishing the task.
