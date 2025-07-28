# Blog Application APIs

A Spring Boot 3 RESTful backend that powers a simple blogging platform.  It provides JWT-secure CRUD APIs for **Users, Categories, and Posts**, including pagination, search, and image-upload capabilities.

---

## Table of Contents
1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Architecture](#architecture)
4. [Database Schema](#database-schema)
5. [Getting Started](#getting-started)
   * [Prerequisites](#prerequisites)
   * [Installation](#installation)
   * [Local Run](#local-run)
   * [Running Tests](#running-tests)
6. [API Reference](#api-reference)
7. [Project Structure](#project-structure)
8. [Contribution Guide](#contribution-guide)
9. [License](#license)

---

## Features
* **User management** – register, update, delete, and list users.
* **Post management** – create, read, update, delete posts; filter by category or user; full-text search.
* **Category management** – CRUD operations on categories.
* **ModelMapper** – automatic DTO ↔ entity conversion.
* **Validation** – bean-level validation using Hibernate Validator.
* **MySQL persistence** – Spring Data JPA with automatic schema update.
* **Error handling** – unified `ApiResponse` objects and custom exceptions.
* **DevTools & hot reload** – for a smooth dev experience.

> Planned / optional extras: JWT authentication, Swagger/OpenAPI docs, Docker, cloud deployment.

---

## Tech Stack
| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.5.3 |
| Build | Maven |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL 8.x |
| Mapping | ModelMapper 3.2.2 |
| Boilerplate reduction | Lombok |
| Validation | Jakarta Bean Validation (Hibernate Validator) |
| Testing | Spring Boot Starter Test, JUnit 5 |

---

## Architecture
```
┌──────────┐      DTOs      ┌────────────┐      Entities      ┌─────────┐
│  Client  │ ─────────────► │  Controller│ ────────────────► │ Service │
└──────────┘  JSON / HTTP   └────────────┘   business logic   └─────────┘
                                            ▲                │  Repository
                                            │                ▼
                                         ModelMapper     Spring Data JPA
                                            │                ▲
                                            └────────────────┘
```
The project follows a typical layered architecture: Controller → Service → Repository.  DTOs keep the REST layer decoupled from JPA entities.

---

## Database Schema
```
user (id PK, name, email, password, about)
category (category_id PK, title, description)
post (post_id PK, title, content, image_name, added_date,
      category_id FK → category, user_id FK → user)
```
Hibernate will create/update these tables automatically on application start (`spring.jpa.hibernate.ddl-auto=update`).

---

## Getting Started
### Prerequisites
* **Java 17** or higher
* **Maven 3.9+**
* **MySQL 8** running locally (or update connection URL)

### Installation
```bash
git clone <repo-url>
cd demo
```

### Configuration
Edit `src/main/resources/application.properties` if your DB credentials differ:
```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/blog_app_apos
spring.datasource.username=<user>
spring.datasource.password=<password>
```

### Local Run
```bash
# build & start
the first time
the database must exist (empty is fine)
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`.

### Running Tests
```bash
mvn test
```

---

## API Reference
Base path: `/api`

### User
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users/` | Create user |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |
| GET | `/users/` | List users |
| GET | `/users/{id}` | Get single user |

### Category
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/categories/` | Create category |
| PUT | `/categories/{id}` | Update category |
| DELETE | `/categories/{id}` | Delete category |
| GET | `/categories/` | List categories |
| GET | `/categories/{id}` | Get single category |

### Post
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/user/{userId}/category/{categoryId}/posts` | Create post |
| GET | `/user/{userId}/posts` | List posts by user |
| GET | `/category/{categoryId}/posts` | List posts by category |
| GET | `/posts` | List all posts |
| GET | `/post/{postId}` | Get single post |
| PUT | `/posts/{postId}` | Update post |
| DELETE | `/posts/{postId}` | Delete post |

> You can explore further using Postman or Curl.  Swagger UI will be added soon.

---

## Project Structure
```
src/main/java/com/blog
├── controller      # REST controllers
├── entities        # JPA entities
├── payloads        # DTOs & API responses
├── repositories    # Spring Data JPA repositories
├── services        # Interfaces
└── services/impl   # Implementations

src/main/resources
├── application.properties
└── static / templates (if needed)
```

---

## Contribution Guide
1. Fork the repository & create a branch: `feature/<name>`.
2. Write clear, concise commits following Conventional Commits.
3. Ensure `mvn test` passes locally.
4. Open a pull request describing **what** and **why**.

We abide by the [Conventional Commit](https://www.conventionalcommits.org) spec and the [Contributor Covenant](https://www.contributor-covenant.org/) code of conduct.

---

## License
This project is licensed under the MIT License – see [`LICENSE`](LICENSE) for details.
