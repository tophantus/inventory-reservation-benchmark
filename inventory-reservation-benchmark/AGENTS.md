# AGENTS.md

## Project

Inventory Reservation Benchmark — Java 21, Spring Boot, PostgreSQL, Redis, Liquibase, Maven.

The project benchmarks inventory reservation strategies:

1. PostgreSQL pessimistic locking (`FOR UPDATE`)
2. Redis-based reservation
3. PostgreSQL reservation pool (`FOR UPDATE SKIP LOCKED`)

## Architecture

Use **Modular Monolith + Hexagonal Architecture + CQRS**.

```text
modules/
  <module>/
    domain/
    application/
    adapter/
```

### Dependency rules

```text
Inbound Adapter
      ↓
Inbound Port
      ↓
Application Handler
      ↓
Domain
      ↓
Outbound Port
      ↑
Outbound Adapter
      ↓
Infrastructure
```

* Domain must not depend on Spring, JPA, Redis, RabbitMQ, or HTTP.
* Application depends only on domain and ports.
* Controllers call inbound use cases, never repositories directly.
* Application must not depend directly on JPA, Redis, or infrastructure implementations.
* Persistence implementations stay inside their business module's `adapter/outbound`.
* `infrastructure/` is for shared technical configuration and infrastructure.
* Do not create unnecessary abstractions.

## Persistence

Keep domain models separate from JPA entities.

```text
Domain Model
    ↕
JPA Mapper
    ↕
JPA Entity
    ↓
Spring Data Repository
```

Use Liquibase as the database schema source of truth.

```text
spring.jpa.hibernate.ddl-auto=validate
```

Never use Hibernate schema generation for migrations.

## CQRS

* Commands mutate state.
* Queries read state.
* Do not introduce separate read/write databases unless required.
* Do not force CQRS abstractions onto trivial operations.

## Benchmark Rules

All strategies must expose the same business API and workload.

Only the concurrency/reservation implementation should differ.

Prioritize:

* correctness / no overselling
* throughput
* p50/p95/p99 latency
* lock contention
* database connections
* Redis performance
* deadlocks / failures

Do not change business semantics merely to improve benchmark results.

## Code Style

* Java 21.
* Lombok is allowed.
* Prefer constructor injection.
* Avoid `@Data` on domain entities.
* Keep business rules in domain/application, not controllers or persistence adapters.
* Prefer focused unit and integration tests over excessive mocking.

## Implementation Principle

Do not over-engineer.

Before adding a layer, interface, abstraction, or module, verify that it provides a real architectural or testing benefit.
