# Elite Sports Equipment

Full‑stack Spring Boot web application for browsing, purchasing, and managing sports equipment. The app includes a product catalog, search, cart, orders, favorites, user profiles, and role‑based moderation/admin tools.

## Overview

Traditional MVC app with Spring Boot + Thymeleaf using a layered architecture (controller → service → repository) to keep web, business logic, and persistence concerns separated.

## Key Features

- Product catalog (belts, straps, sleeves) with detail pages
- Search & filtering by brand/type
- Cart workflow and order creation with order details
- Favorites per user
- Role‑based access:
  - ADMIN: add products and manage roles
- Secure authentication flow with Spring Security
- Cloudinary image uploads
- i18n (EN/BG)
- Validation with Jakarta Validation

## Tech Stack

- Java 21
- Spring Boot 3.5.x (Web, Security, Thymeleaf, Validation, Data JPA)
- Hibernate 6
- MySQL (production) + H2 (tests)
- MapStruct
- Cloudinary Java SDK
- Gradle

## Project Structure

Source code is under `src/main/java/bg/softuni/eliteSportsEquipment`:

- `config`: Spring configuration (security, web, cloudinary, i18n)
- `exception`: custom exceptions and handlers
- `web`: MVC controllers
- `service`: business logic
- `repository`: Spring Data JPA repositories
- `model`:
  - `entity`: JPA entities
  - `dto`: view/request DTOs
  - `mapper`: MapStruct mappers
  - `validation`: custom validators

Templates and static assets `src/main/resources`:

- `templates`: Thymeleaf views + fragments
- `static/css`: styles
- `static/images`: images
- `i18n`: message bundles

Server runs on http://localhost:8080.