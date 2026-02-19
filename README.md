# PROG3360-Assignment2-group4
# Feature Flags, Microservices, Docker Compose and CI/CD Pipelines
# Name: Priyanshi Jadeja, Ayush Patel, Tirth Patel, Nisarg Khyali
# Course: Software Delivery & Release Management

Project Overview:
This project consists of two Spring Boot microservices, namley, product-service and order-service.
This system demonstrates: Trunk based development, feature flag implementation using unleash, also multi-stage docker builds, docker compose for health checks, Github Actions CI/CD pipeline.

Architecture:
Service:
Product Service: Port 8081
Order Service: Port 8082
Unleash Server: Port 4242
PostgresSQL (used by unleash)
All services are connected using Docker compose and docker file.

# Setup Instructions:
The following needs to be installed for this project:
-Java JDK21
-Docker Desktop
-Git

How to run the application:
-Build & start using docker compose: docker compose up --build

# CI/CD:
This project is using GitHub Actions to automatically build, test and run Docker containers on each and every push and pull request to main branch.
The pipeline includes:

Build and Test Job:
-Builds product-service
-Builds order-service
-Runs unit tests
-Uploads test reports

Docker Integration Job:
-Builds Docker images
-Starts containers
-Waits for health checks
-Tests API endpoints
-Shuts down containers

Feature Flag Validation Job:
-Starts services
-Verifies Unleash server is reachable
-Validates product endpoint
-Shuts down containers

The pipeline runs automatically on:
-Push to main branch
-Pull request to main branch
