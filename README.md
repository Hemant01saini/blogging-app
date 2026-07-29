# Blog Application

A production-style Blog Application built using Spring Boot, Spring Security, JWT Authentication, JPA/Hibernate, MySQL, and AWS S3.

The project allows users to create posts, like posts, comment, follow users, save posts, upload profile images, and view a personalized feed.

---

## Features

- User Registration
- JWT Login Authentication
- Role Based Authorization
- Create / Update / Delete Posts
- Categories & Tags
- Comments
- Like / Unlike Posts
- Follow / Unfollow Users
- Save Posts
- Personalized Feed
- Profile Image Upload (AWS S3)
- Search APIs
- Pagination
- Spring Cache
- Scheduler for Seen Posts Cleanup
- Global Exception Handling
- Swagger Documentation

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- AWS S3
- Maven
- Lombok
- Swagger (OpenAPI)

---

## Project Structure

src/main/java

controller

service

repository

entity

dto

mapper

security

config

scheduler

exception

util

---

## Authentication

JWT Based Authentication

After login a JWT token is generated.

All secured APIs require Authorization Header.

Bearer Token

---

## Database

MySQL

Relationships Used

- OneToOne
- OneToMany
- ManyToOne
- ManyToMany

---

## APIs

- User APIs
- Authentication APIs
- Post APIs
- Category APIs
- Tag APIs
- Comment APIs
- Follow APIs
- Saved Post APIs
- Feed APIs
- Search APIs
- Media APIs

---

## Additional Features

- Spring Cache
- Scheduler
- Pagination
- Search
- Logging
- Validation
- ResponseEntity

---

## API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## Author

Hemant Saini
