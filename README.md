# inventory-reservation-benchmark
Benchmarking inventory reservation strategies under high concurrency, including pessimistic locking, Redis-based reservations, and SKIP LOCKED reservation pools.

## Local stack

Copy `.env.example` to `.env` and set the PostgreSQL and Grafana credentials. Start the backend, PostgreSQL, Redis, Prometheus, and Grafana with:

```bash
docker compose --env-file .env -f infrastructure/docker-compose.yml config
docker compose --env-file .env -f infrastructure/docker-compose.yml up --build
```

The backend is available at `http://localhost:8080`; its health and Prometheus endpoints are `/actuator/health` and `/actuator/prometheus`. Prometheus is available at `http://localhost:9090` and Grafana at `http://localhost:3000`.

Run the reservation benchmark on demand after starting the stack:

```bash
docker compose -f infrastructure/docker-compose.yml run --rm k6
```
