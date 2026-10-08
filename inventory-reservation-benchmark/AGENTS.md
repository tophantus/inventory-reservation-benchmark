# AGENTS.md

## Project

Backend — Java 21, Spring Boot, PostgreSQL, Redis, Liquibase, Maven.

## Architecture

Use **Modular Monolith + Hexagonal Architecture + CQRS**.

```text
com.tophantu.inventory/
├── customer/
├── shop/
├── product/
├── inventory/
└── reservation/
```

Each module:

```text
<module>/
├── domain/
├── application/
└── adapter/
```

## Domain

```text
domain/
├── model/
├── valueobject/
├── enum/
└── exception/
```

Domain must not depend on Spring, JPA, Redis, or HTTP.

## Application

Command and Query are separated.

```text
application/
├── command/
│   ├── port/
│   │   ├── inbound/
│   │   └── outbound/
│   ├── handler/
│   └── dto/
│
└── query/
    ├── port/
    │   ├── inbound/
    │   └── outbound/
    ├── handler/
    └── dto/
```

### Command

```text
Command
  ↓
Inbound Port
  ↓
Command Handler
  ↓
Outbound Port
  ↓
Adapter
```

Commands change state.

### Query

```text
Query
  ↓
Inbound Port
  ↓
Query Handler
  ↓
Outbound Port
  ↓
Adapter
```

Queries only read state.

Rules:

* `inbound/` defines use-case contracts.
* `outbound/` defines dependencies required by the handler.
* `handler/` contains application orchestration.
* `dto/` contains command/query input and output models.
* Command and Query must not share unnecessary ports or handlers.
* Do not over-engineer CQRS.

## Adapter

```text
adapter/
├── inbound/
│   └── http/
│       ├── request/
│       ├── response/
│       ├── <Module>Controller.java
│       └── <Module>HttpMapper.java
│
└── outbound/
    └── persistence/
        └── jpa/
            ├── <Module>JpaEntity.java
            ├── <Module>JpaRepository.java
            ├── <Module>JpaMapper.java
            └── <Module>CommandRepositoryAdapter.java
```

Flow:

```text
HTTP
 ↓
Inbound Port
 ↓
Handler
 ↓
Domain
 ↓
Outbound Port
 ↓
Adapter
```

Rules:

* Controller depends only on inbound ports.
* Handler depends only on domain and outbound ports.
* Persistence adapter implements outbound ports.
* Keep domain models separate from JPA entities.
* No business logic in controllers or persistence adapters.
* Modules communicate through application ports, not repositories/adapters.

## Persistence

* Liquibase is the database schema source of truth.
* Use PostgreSQL-compatible migrations.
* `ddl-auto: validate`.
* Never use Hibernate to create or modify the schema.

## Error Handling

* Use shared `BusinessException` with module-specific `ErrorCode`.
* Define business error codes inside each module's `domain/exception/` (e.g. `CustomerErrorCode`).
* Do not create one global business error enum for all modules.
* Keep `ErrorCode` framework-independent; do not put `HttpStatus` in domain.
* Let `GlobalExceptionHandler` handle `BusinessException` and map it to HTTP responses.
* Use `ApiResponse` only in the HTTP adapter layer.

## Code Style

* Java 21.
* Lombok is allowed.
* Prefer constructor injection.
* Avoid `@Data` on domain models.
* Prefer focused unit/integration tests.
* Avoid unnecessary layers, interfaces, and abstractions.
