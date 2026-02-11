# PROG3360-Assignment2-Priyanshi
# Feature Flags, Microservices, Docker Compose and CI/CD Pipelines
# Name: Priyanshi Jadeja
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

How to run the application:
Start everything using command: docker compose up -d --build


# Setup Instructions:
The following needs to be installed for this project:
-Java JDK21
-Docker Desktop
-Git

How to run the application:
-Build & start using docker compose: docker compose up --build

# CI/CD:
This project is using GitHub Actions to automatically build, test and run Docker containers on each and every push and pull request to main branch.
