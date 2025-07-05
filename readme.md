OpenApi Docs - http://localhost:8080/swagger-ui/index.html#/
H2 Console - http://localhost:8080/h2-console
Project Structure

expensetracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/expensetracker/
│   │   │       ├── config/       # Security, Swagger, Exception handlers
│   │   │       ├── controller/   # REST endpoints
│   │   │       ├── dto/          # Request/response objects
│   │   │       ├── model/        # JPA entities
│   │   │       ├── repository/   # Spring Data JPA
│   │   │       ├── service/      # Business logic
│   │   │       ├── util/         # Fraud detection rules
│   │   │       └── ExpensetrackerApplication.java
│   │   └── resources/
│   │       ├── application.yml   # YAML > properties
│   │       ├── data.sql          # Initial test data
│   │       └── schema.sql        # DB schema
│   └── test/
│       ├── java/
│       │   └── com/example/expensetracker/
│       │       ├── integration/  # @SpringBootTest
│       │       ├── unit/         # @WebMvcTest, @DataJpaTest
│       │       └── cucumber/     # BDD tests
│       └── resources/
│           └── application-test.yml

Mile Stone
## 🧱 Project Summary: Expense Tracker API (Spring Boot)

### ✅ Key Accomplishments So Far
| Area                          | Details |
|-------------------------------|---------|
| **Project Type**              | Spring Boot 3.5.3 (Java 21) |
| **Database**                  | In-memory H2 + Flyway for schema migration |
| **API Documentation**         | Swagger UI (via `springdoc-openapi-starter-webmvc-ui:2.8.9`) |
| **Security**                  | Spring Security with in-memory user auth |
| **Logging**                   | Custom Logback config for clean, timestamped output |
| **Live Reloading**            | Spring DevTools with IntelliJ auto-rebuild support |
| **Error Handling**            | `@ControllerAdvice`-based `GlobalExceptionHandler` for clean 404 messages |
| **DTO Mapping**               | Stateless utility `ExpenseMapper` with static methods |
| **API Tested So Far**         | `GET /api/expenses/{id}` with not-found fallback |

---

## 🧩 Architectural Layers & Flow

### 🌐 API Layer
- **Controller:** `ExpenseController`
    - Maps HTTP endpoints like `/api/expenses/{id}`
    - Returns DTOs, handles input params
    - Throws `ResourceNotFoundException` if entity not found

### ⚙️ Service Layer
- **`ExpenseServiceImpl`**
    - Business logic: fetch data, map to DTOs
    - Uses `ExpenseRepository` for DB access
    - Logs key actions (e.g. `Inside getExpenseById`)

### 🧪 Exception Handling
- **`GlobalExceptionHandler`**
    - Handles errors like 404 (not found)
    - Returns clean text message + HTTP code

### 💾 Data Layer
- **`Expense` Entity**
    - JPA-managed table with fields: `id`, `title`, `amount`, `category`, `date`

- **`ExpenseRepository`**
    - Extends `JpaRepository`, exposing basic CRUD

- **Database:**
    - In-memory H2 (with `/h2-console`)
    - Table auto-created by Hibernate
    - No migrations yet, but Flyway is integrated

---

## 🔁 Request Lifecycle Flow

1. 🧑 User hits `/api/expenses/10`
2. 🎯 `ExpenseController` receives ID → delegates to service
3. 🧠 `ExpenseServiceImpl` fetches from `ExpenseRepository`
4. ❌ If not found → throws `ResourceNotFoundException`
5. 🛡️ `GlobalExceptionHandler` catches and formats 404 response
6. 📋 Swagger UI shows proper API contract and response format
7. 📜 Logs reflect request, processing, and errors in real time

---

From here, you're perfectly positioned to:
- Add `POST`, `PUT`, and `DELETE` endpoints
- Seed your H2 with dummy data for testing
- Enhance logging with trace IDs or log to file
- Plug in front-end or postman/Scalar-based workflows

Let me know if you’d like a visual diagram or want to version this as a `README.md` summary next. This backend's got some serious swagger 🛠️🔥
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


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
| **SpringDoc OpenAPI**     | Swagger UI for API docs                |
| **Spring DevTools**       | Live reload and auto-restart support   |
| **IntelliJ IDEA**         | IDE with auto-build config             |

---

## 📐 Project Structure & Layered Design

```
com.example.expensetracker
├── controller         // Handles HTTP endpoints
├── dto                // Request and response objects
├── entity             // JPA entities representing DB tables
├── exception          // Custom exceptions + global handlers
├── mapper             // Converts entity ↔ DTO
├── repository         // JPA Repository interfaces
├── service            // Business logic
├── config             // Swagger, DevTools configs (if needed)
└── application.yml    // Central config file
```

---

## 🔁 API Call Flow (High-Level)

```text
Client → Controller → Service → Repository → DB
               ↓         ↓         ↑
       Validation   Logging   DTO Mapping
               ↓         ↓         ↑
      Error Handling  Entity ↔ DTO → Response
```

---

## 🧠 Spring Boot Concepts (Explained in Detail)

### 1. `@RestController`
- Combines `@Controller` + `@ResponseBody`
- Every method returns JSON directly

### 2. `@RequestMapping` & `@GetMapping`, `@PostMapping`, etc.
- Maps HTTP requests to Java methods
- Organized under `/api/expenses`

### 3. DTOs
- Separates input/output from your internal `@Entity`
- Keeps code safe from accidental data leaks

### 4. JPA + Hibernate
- Your `Expense` class is annotated with `@Entity`
- `ExpenseRepository` extends `JpaRepository<Expense, Long>`, giving you:
  - `findAll()`, `findById()`, `save()`, `deleteById()`, etc.

### 5. Validation (`@Valid`, `@NotBlank`, `@DecimalMin`)
- Applies constraints to request DTOs
- Handled via `MethodArgumentNotValidException`

### 6. Global Error Handling: `@RestControllerAdvice`
- Catches errors app-wide
- Returns clean JSON with status, timestamp, and error messages

```json
{
  "status": 400,
  "timestamp": "2025-07-01T17:45:00",
  "errors": ["Title is required", "Amount must be greater than 0"]
}
```

### 7. Swagger UI (`springdoc-openapi-starter-webmvc-ui`)
- Auto-generates docs at: `http://localhost:8080/swagger-ui/index.html`
- Use `@Operation` and `@ApiResponses` to describe each endpoint
- Group endpoints with `@Tag`

### 8. Logging with Logback
- Output includes timestamp, thread, logger, and log level
- Example:
  ```
  2025-07-01 15:55:11 INFO  ExpenseServiceImpl : Inside getExpenseById method for ID: 10
  ```

### 9. Spring DevTools
- Hot reloads your app on code changes
- Just add this to `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <scope>runtime</scope>
</dependency>
```

**Enable Auto-Build in IntelliJ:**
- `File → Settings → Compiler → Build project automatically`
- Also enable: `Advanced Settings → Allow auto-make to start even if app is running`

---

## 🧪 Endpoints Implemented

| HTTP Method | Endpoint               | Description              |
|-------------|------------------------|--------------------------|
| `GET`       | `/api/expenses`        | Get all expenses         |
| `GET`       | `/api/expenses/{id}`   | Get expense by ID        |
| `POST`      | `/api/expenses`        | Add new expense          |
| `PUT`       | `/api/expenses/{id}`   | Update expense           |

Example payload for POST:

```json
{
  "title": "Lunch",
  "amount": 250.00,
  "category": "Food",
  "date": "2025-07-01"
}
```

---

## 🌐 Future Enhancements

- `DELETE /api/expenses/{id}`
- Filtering by category, date, etc.
- Security using Spring Security + JWT
- Dockerized deployment
- Integration + unit tests with `@SpringBootTest` and Mockito

---
