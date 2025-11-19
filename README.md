# Blogging Platform - Spring Boot

A simple Java + Spring Boot project implementing a RESTful API for a personal blogging platform.

## Features
- Create, update, delete, and fetch blog posts
- Search posts by a term (searches title, content, author)
- H2 in-memory database for easy testing
- Validation for request payloads
- Unit tests for service and controller layers

## Run
You can build and run with Maven:

```bash
mvn spring-boot:run
```

API base path: `http://localhost:8080/api/posts`

Examples:
- `POST /api/posts` to create
- `PUT /api/posts/<built-in function id>` to update
- `DELETE /api/posts/<built-in function id>` to delete
- `GET /api/posts/<built-in function id>` to fetch one
- `GET /api/posts` to fetch all
- `GET /api/posts?search=term` to search

## Download
This project zip was generated on 2025-11-18T01:20:52.867385 UTC and is included in this workspace.

## Added features in updated project
- Pagination & sorting via Spring Data `Pageable`. Use query params like `?page=0&size=10&sort=createdAt,desc`.
- Swagger / OpenAPI UI at `/swagger-ui.html` (powered by springdoc).
- Basic HTTP authentication protecting `/api/**` endpoints.
  - Default users: `user` / `password` (ROLE_USER), `admin` / `adminpass` (ROLE_ADMIN).
  - Swagger UI and H2 console are permitted without authentication by default in this config.

## Notes
- For production do not use `{noop}` passwords or in-memory users; use a proper password encoder and persistent user store or JWT/OAuth2.
- You can access the H2 console at `/h2-console`.
