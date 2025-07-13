Here’s your README, freshly updated with Swagger URL fix, project progress, and clarity across sections—and woven together like the backend deserves 💼📘

---

# 🧾 Expense Tracker API – Spring Boot Reference Guide

> A complete developer guide and architectural reference to kickstart clean, scalable Spring Boot projects. This covers all key concepts, annotations, patterns, and tools used in a real-world backend build.

---

## 🔧 Tech Stack

| Tool / Framework         | Purpose                                |
|---------------------------|----------------------------------------|
| **Spring Boot 3.5.3**     | Web framework                          |
| **Java 21**               | Core language                          |
| **Maven**                 | Dependency & build management          |
| **H2 (in-memory)**        | Lightweight database for dev/test      |
| **Hibernate (JPA)**       | ORM for DB operations                  |
| **Logback**               | Logging framework                      |
| **Spring Security**       | In-memory authentication               |
| **SpringDoc OpenAPI**     | Swagger UI for API docs                |
| **Spring DevTools**       | Live reload and auto-restart support   |
| **IntelliJ IDEA**         | IDE with auto-build config             |

---

## 🌐 Local Dev URLs

| Feature       | URL                                                        |
|---------------|-------------------------------------------------------------|
| **Swagger UI**| [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html) |
| **H2 Console**| [`http://localhost:8080/h2-console`](http://localhost:8080/h2-console) |

---

## 📐 Project Structure

```
expensetracker/
├── src/
│   ├── main/
│   │   ├── java/com/example/expensetracker/
│   │   │   ├── config/       # Security, Swagger, Exception handlers
│   │   │   ├── controller/   # REST endpoints
│   │   │   ├── dto/          # Request/response objects
│   │   │   ├── model/        # JPA entities
│   │   │   ├── repository/   # Spring Data JPA
│   │   │   ├── service/      # Business logic
│   │   │   ├── util/         # Fraud detection rules
│   │   │   └── ExpensetrackerApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── data.sql
│   │       └── schema.sql
│   └── test/
│       ├── java/com/example/expensetracker/
│       │   ├── integration/  # @SpringBootTest
│       │   ├── unit/         # @WebMvcTest, @DataJpaTest
│       │   └── cucumber/     # BDD tests
│       └── resources/
│           └── application-test.yml
```

---

## 🔁 API Request Flow

```text
Client → Controller → Service → Repository → DB
               ↓         ↓         ↑
       Validation   Logging   DTO Mapping
               ↓         ↓         ↑
      Error Handling  Entity ↔ DTO → Response
```

---

## ✅ Key Accomplishments So Far

| Area                    | Details                                                  |
|-------------------------|----------------------------------------------------------|
| **Deployment**          | CI/CD via Render with Docker (multi-stage build)         |
| **Root Redirect**       | `HomeController` routes `/` → `/swagger-ui/index.html`   |
| **Error Handling**      | Global 404 fallback with `@ControllerAdvice`             |
| **DTO Mapping**         | Stateless mapper for clean model-to-response conversion  |
| **Logging**             | Logback with precise output formatting and timestamps    |
| **Security**            | In-memory Spring auth (expandable to JWT later)          |
| **DevTools**            | Auto-reload with IntelliJ                                 |
| **Data Source**         | H2 DB preloaded via `data.sql` for testing               |
| **Flyway Migration**    | Ready but not yet applied                                |

---

## 🧪 Endpoints Implemented

| Method | Endpoint                 | Action             |
|--------|--------------------------|--------------------|
| `GET`  | `/api/expenses`          | List all expenses  |
| `GET`  | `/api/expenses/{id}`     | Get by ID          |
| `POST` | `/api/expenses`          | Add new expense    |
| `PUT`  | `/api/expenses/{id}`     | Update expense     |

Example `POST` body:
```json
{
  "title": "Lunch",
  "amount": 250.00,
  "category": "Food",
  "date": "2025-07-01"
}
```

---

## 🧠 Core Spring Boot Concepts

- `@RestController`: Combines response body with controller
- `@RequestMapping` / `@GetMapping`: Endpoint exposure
- `@Entity`: Marks DB model
- `@Repository`: Auto CRUD with JPA
- `@ControllerAdvice`: Centralized error handling
- `@Valid`: Request validation with Bean Validation annotations

---

## 📋 Swagger UI Customizations

- `@Operation`: Describes endpoint
- `@ApiResponses`: Maps 200/400/404 cases
- `@Tag`: Logical grouping
- Auto-generated docs with `springdoc-openapi-starter-webmvc-ui`

---

## 🔮 Next Steps

- Add `DELETE /api/expenses/{id}`
- Enable filtering by `category`, `date`, etc.
- Add pagination
- Integrate JWT auth
- Expand Flyway for DB versioning
- Write integration + BDD tests

---

Let me know if you’d like me to version this, scaffold new endpoints, or generate a Markdown-compatible version for GitHub `README.md`. This backend’s ready to fly 🚀🛠️
