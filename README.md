GSRS Backend

Government Service Request System — Backend API

The GSRS backend is a RESTful API built with Spring Boot that powers the Government Service Request System. It handles authentication, request management, department management, and administrative operations.

The API supports multiple roles including citizens, department staff, and administrators.

Live API

Backend URL
https://gsrs-backend.onrender.com

Example API base path
https://gsrs-backend.onrender.com/api

Note: The backend runs on the Render free tier and may take 30–60 seconds to wake up after inactivity.

Tech Stack

Backend Framework
Spring Boot 4

Language
Java 17

ORM
Spring Data JPA / Hibernate

Database
PostgreSQL

Authentication
JWT (JSON Web Token)

Security
Spring Security

Build Tool
Maven

Deployment
Docker + Render

Features

User authentication using JWT

Role-based access control

Citizens can submit and track service requests

Department staff can update request statuses

Admins can manage users, departments, and system statistics

RESTful API design

Pagination support for large datasets

Architecture

The backend follows a layered architecture.

Controller Layer
Handles HTTP requests and responses.

Service Layer
Contains business logic.

Repository Layer
Handles database operations using Spring Data JPA.

Entity Layer
Represents database tables.

DTO Layer
Transfers data between client and server.

Security Layer
Handles JWT authentication and authorization.

Package Structure

Base package

com.gsrs.GSRS

Important packages

config
Security configuration and JWT utilities

security
JWT authentication filter

controller
REST API controllers

service
Business logic interfaces and implementations

repository
Spring Data JPA repositories

entity
Database entities

dto.request
Incoming request DTOs

dto.response
Outgoing response DTOs

exception
Global exception handling

API Endpoints

Authentication

POST /api/auth/register
Register a new user

POST /api/auth/login
Login and receive JWT token

Departments

GET /api/departments
Get all departments

POST /api/departments
Create a department (Admin only)

DELETE /api/departments/{id}
Delete a department (Admin only)

Service Requests

POST /api/requests
Submit a new service request (Citizen)

GET /api/requests/citizen/{citizenId}
Get requests created by a citizen

GET /api/requests
Get all requests (Admin or Department Staff)

PATCH /api/requests/{id}/status
Update request status (Department Staff)

Admin Operations

GET /api/admin/requests
Get filtered requests

GET /api/admin/requests/stats
Get system statistics

PUT /api/admin/requests/{id}/assign
Assign request to a department

GET /api/admin/departments/{id}/requests
Get requests belonging to a department

Users

GET /api/users
Get all users (Admin)

DELETE /api/users/{id}
Delete user (Admin)

Security

Authentication is implemented using JSON Web Tokens.

All protected endpoints require a Bearer token in the Authorization header.

Example

Authorization: Bearer your_token_here

Tokens include:

user email

user role

user ID

Token expiration is set to 24 hours.

Running Locally

Prerequisites

Java 17
Maven 3.9+
PostgreSQL
Node.js (for frontend)

Step 1 — Create Database

CREATE DATABASE gsrs_db;

Step 2 — Configure Environment Variables

Example

DB_USERNAME=your_db_username

DB_PASSWORD=your_db_password

JWT_SECRET=your_secure_random_secret

Step 3 — Run Application

Start the Spring Boot application.

The server runs on

http://localhost:8080

Docker

The backend is containerized using a multi-stage Docker build.

Stage 1
Build the application using Maven.

Stage 2
Run the generated JAR using a lightweight Java runtime.

Deployment

The backend is deployed on Render as a Docker web service.

Deployment components

Backend API
Render Web Service

Database
Render PostgreSQL

Frontend
Separate React application

Known Behavior

Because the application runs on the Render free tier, the backend may enter sleep mode after inactivity.

The first request after sleep may take around 30–60 seconds.

License

This project is for educational and portfolio purposes.
