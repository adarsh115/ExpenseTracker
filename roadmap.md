# 🚀 Roadmap – Expense Tracker with Fraud Detection

A modular Spring Boot-based platform evolving from simple expense tracking into a fintech-grade system with fraud detection, authentication, and analytics. This roadmap outlines completed features and upcoming milestones.

---

## ✅ Core Features (Completed)

- [x] CRUD Operations for Expense Entity (`GET`, `POST`, `PUT`, `DELETE`)
- [x] Delete returns deleted item for confirmation
- [x] DTO ↔ Entity mapping with validation
- [x] Logging with Logback (custom patterns)
- [x] Global Exception Handling
- [x] Swagger/OpenAPI Documentation
- [x] Spring DevTools Integration
- [x] README Documentation

---

## 🔜 Milestone 1: Data Access & UX Enhancements

- [x] Filtering by category, date range
- [x] Pagination using Spring Data `Pageable`
- [x] Expense Summarization Endpoint (monthly/category totals)

---

## 🔐 Milestone 2: Security & Role Management

- [ ] JWT Token-based Authentication
- [ ] Spring Security Configuration
- [ ] Role-Based Access Control (`USER`, `ADMIN`, `AUDITOR`)
- [ ] Endpoint access control by role

---

## 🧪 Milestone 3: Testing & QA Workflow

- [ ] Integration Testing with MockMvc
- [ ] Unit Tests with Mockito
- [ ] Embedded H2 + SQL Seeders for test isolation

---

## ☁️ Milestone 4: Deployment & DevOps

- [ ] Dockerize application for consistent environments
- [ ] Deploy to platforms (Heroku / Railway / Render)
- [ ] CI/CD Pipeline (auto build, test, deploy)

---

## 🔎 Milestone 5: Fraud Detection (Fintech Extension)

- [ ] Drools Rule Engine Integration
- [ ] Heuristic-based fraud rules (e.g., location anomalies, spending spikes)
- [ ] `POST /api/fraud-checks` endpoint
- [ ] Audit trail for flagged transactions
- [ ] Role-restricted access to fraud logs

---

## 📦 Architecture & Codebase Highlights

- Feature-based modular folder structure (`expense/`, `auth/`, `fraud/`, etc.)
- Clean separation of concerns for controller, service, DTO, repository, and config
- Domain-first scalability aligned with real-world microservice principles

---

This roadmap evolves with each milestone. PRs and features are organized under branches like `feature/filtering`, `security/jwt-auth`, or `fraud/rule-engine` for clean development workflows.
