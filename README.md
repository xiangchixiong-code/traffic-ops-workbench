# traffic-ops-workbench

Traffic Ops Workbench is a compact Spring Boot backend demo for transportation operations and infrastructure management.

It is intentionally scoped to look like a real internship project for:

- Ningxia Jiaotou / smart transportation / information systems roles
- operator enterprise digital support roles
- implementation / support / maintenance / software testing roles

## What it does

- road section master data
- device asset registry
- inspection records
- fault ticket workflow
- dashboard statistics
- operation logs

## Tech stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- H2 database

## Why this project exists

The goal is not to look fancy. The goal is to be easy to explain in an interview:

- a transportation operations domain
- predictable database tables
- REST APIs
- validation and global error handling
- seeded demo data
- statistics you can talk through

## Quick start

1. Install Java 21.
2. Run `./mvnw test` or `mvnw.cmd test`.
3. Run the application.
4. Visit the APIs under `/api`.

## Main endpoints

- `GET /api/health`
- `GET /api/dashboard`
- `GET /api/devices`
- `POST /api/devices`
- `PATCH /api/devices/{id}/status`
- `GET /api/tickets`
- `POST /api/tickets`
- `PATCH /api/tickets/{id}/status`

## Resume-ready bullets

- Designed a transportation operations backend with road sections, device assets, inspection records, and fault tickets.
- Implemented REST APIs, validation, pagination, and global error handling with Spring Boot.
- Seeded demo data and dashboard metrics for maintenance status, ticket status, and device health.
- Organized the project for interview walkthroughs and GitHub portfolio use.

