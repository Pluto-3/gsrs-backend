GSRS Backend

Government Service Request System — Backend API

The GSRS Backend is a RESTful API built with Spring Boot that powers the Government Service Request System.
It manages authentication, service request handling, department management, and administrative operations.

The system supports multiple roles:

Citizens – submit and track service requests

Department Staff – process and update requests

Administrators – manage users, departments, and system activity

Live API

Backend URL
https://gsrs-backend.onrender.com

Base API path

https://gsrs-backend.onrender.com/api

Note: The backend runs on the Render free tier.
If the service has been inactive, the first request may take 30–60 seconds to respond.

Tech Stack

Backend Framework
Spring Boot 4

Language
Java 17

ORM
Spring Data JPA / Hibernate

Database
PostgreSQL

Security
Spring Security

Authentication
JWT (JSON Web Tokens)

Build Tool
Maven

Deployment
Docker + Render

Features

JWT-based authentication

Role-based access control (RBAC)

RESTful API architecture

Citizens can submit and track service requests

Department staff update request statuses

Admins manage departments, users, and requests

Pagination support for large datasets

Global exception handling

DTO-based API structure

Architecture

The application follows a layered architecture.

Client Application
        ↓
Spring Boot REST API
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
PostgreSQL Database
Layers

Controller Layer
Handles HTTP requests and responses.

Service Layer
Contains business logic and coordinates operations.

Repository Layer
Handles database access using Spring Data JPA.

Entity Layer
Represents database tables.

DTO Layer
Transfers structured data between client and server.

Security Layer
Handles JWT authentication and authorization.

Package Structure

Base package

com.gsrs.GSRS

Project structure

config
security
controller
service
repository
entity
dto.request
dto.response
exception
Key Packages

config
Security configuration and JWT utilities.

security
JWT authentication filter.

controller
REST API endpoints.

service
Business logic and service interfaces.

repository
Database access layer using Spring Data JPA.

entity
Database entities.

dto.request
Incoming request DTOs.

dto.response
Outgoing response DTOs.

exception
Global exception handling.

Database Schema
Users

Fields

id (UUID)

name

email

password

role

Roles supported

CITIZEN

ADMIN

DEPARTMENT_STAFF

Departments

Fields

id (UUID)

name

Service Requests

Fields

id (UUID)

title

description

category

status

created_at

updated_at

citizen_id

department_id

Statuses

SUBMITTED

IN_PROGRESS

RESOLVED

REJECTED

API Endpoints
Authentication

Register a new user

POST /api/auth/register

Login and receive JWT token

POST /api/auth/login
Departments

Get all departments

GET /api/departments

Create a department (Admin only)

POST /api/departments

Delete a department (Admin only)

DELETE /api/departments/{id}
Service Requests

Submit a service request (Citizen)

POST /api/requests

Get requests created by a citizen

GET /api/requests/citizen/{citizenId}

Get all requests (Admin or Department Staff)

GET /api/requests

Update request status (Department Staff)

PATCH /api/requests/{id}/status
Admin Operations

Get filtered requests

GET /api/admin/requests

Get system statistics

GET /api/admin/requests/stats

Assign a request to a department

PUT /api/admin/requests/{id}/assign

Get requests by department

GET /api/admin/departments/{id}/requests
User Management

Get all users (Admin only)

GET /api/users

Delete user (Admin only)

DELETE /api/users/{id}
Security

Authentication uses JSON Web Tokens (JWT).

All protected endpoints require a Bearer token in the Authorization header.

Example

Authorization: Bearer <token>

Token includes

user email

user role

user ID

Token expiration is 24 hours.

Role-based access control is enforced using Spring Security.

Environment Variables

Example configuration

DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_secure_random_secret_key
Running Locally
Prerequisites

Java 17

Maven 3.9+

PostgreSQL

Create Database
CREATE DATABASE gsrs_db;
Configure Environment Variables

Example

DB_USERNAME=postgres
DB_PASSWORD=your_password
JWT_SECRET=your_secure_random_secret
Run Application

Start the Spring Boot application.

Server runs on

http://localhost:8080

API base URL

http://localhost:8080/api
Docker

The backend is containerized using a multi-stage Docker build.

Stage 1
Build the application using Maven.

Stage 2
Run the generated JAR using a lightweight Java runtime.

The application exposes port 8080.

Deployment

The backend is deployed on Render as a Docker web service.

Infrastructure

Backend API
Render Web Service

Database
Render PostgreSQL

Frontend
Separate React application

Known Behavior

Because the backend runs on the Render free tier, it may enter sleep mode after inactivity.

The first request after sleep may take 30–60 seconds.

License

This project was built for educational and portfolio purposes.
