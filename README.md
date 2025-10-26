FitChoice - README
=================
**Project:** FitChoice (REST API for Gym Membership Management)

**Goal:** Demo application for learning and showcasing Spring Boot, Spring Data JPA, Hibernate, DTOs, functional programming (Streams/Lambda), OpenAPI/Swagger, and unit/integration testing.


### **Short Description:**

FitChoice is a Spring Boot backend application that allows clients to manage gym memberships, optional add-ons (personal trainer, nutritionist), and fitness classes (e.g. Zumba, Aerobics).
It handles registration of clients, selection of membership plans, calculation of total costs, application of discounts for loyal customers, and payment generation.

The project demonstrates key Java and Spring concepts — controllers, services, repositories, DTO mapping, layered architecture, and testing.

## Main functionalities

### Memberships

#### Types available:

Full Fitness – 1 month, gym access only

Gym Pro – 1 month, includes a personal trainer

Gym Star – 1 month, includes both trainer and nutritionist

Each membership stores its start and expiry date and a generated payment.


### Extras

#### Clients can optionally add:

Personal Trainer (extra cost)

Nutritionist (extra cost)

Fitness Classes (extra cost) such as Zumba or Aerobic

#### Discounts

If a client purchases the same membership type more than 3 times (and all previous ones were paid), a 15% loyalty discount is automatically applied.

#### Payments

Automatically calculated and persisted when a new membership is created.

Includes final price, start date, expiry date, and payment date.

## REST API Overview

### Clients

POST /api/clients – register a new client

GET /api/clients/{id} – retrieve a client by ID

GET /api/clients – list all clients

### Memberships

POST /api/memberships – create a membership for an existing client (with optional extras)

GET /api/memberships/{id} – view a membership

GET /api/memberships – list all memberships or by client

Automatically generates payment and stores all related data.

### Trainers / Nutritionists / Fitness Classes

CRUD endpoints available for each resource (/api/trainers, /api/nutritionists, /api/fitnessClasses)

### Payments

GET /api/payments – view or list all payments

All endpoints are documented and accessible via Swagger UI.

## Packages / Structure (summary)
* `com.fitchoice.fitchoice.model.entity` – JPA entities
* `com.fitchoice.fitchoice.model.dto` – Data Transfer Objects
* `com.fitchoice.fitchoice.repository` – Spring Data JPA repositories
* `com.fitchoice.fitchoice.service` – Business logic services
* `com.fitchoice.fitchoice.controller` – REST controllers
* `com.fitchoice.fitchoice.config` –  DataLoader, Swagger/OpenAPI config

## Technologies & Dependencies
* Java 21 
* Spring Boot 3.x 
* Spring Web 
* Spring Data JPA 
* Hibernate ORM 
* springdoc-openapi (Swagger UI)
* PostgreSQL (main DB)
* H2 (used for testing)
* Lombok 
* JUnit 5, Mockito 
* Maven

Example of main dependencies from _`_pom.xml_`_:
* `spring-boot-starter-web`
* `spring-boot-starter-data-jpa`
* ``springdoc-openapi-starter-webmvc-ui``
* h2 (runtime)
* lombok
* `spring-boot-starter-test` + `spring-security-test`

## How to run the project (locally)
1. Clone the repository or open the project in your IDE (IntelliJ recommended). 
2. Make sure Java 21 and Maven are installed. 
3. Default DB settings: H2 in-memory (for development).
   If you want to use PostgreSQL or MySQL instead, modify the `application.properties` file. 
4. Build & run:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
   
5. Access the API at: http://localhost:8080
6. Swagger UI: http://localhost:8080/swagger-ui/index.html

## Example usage (Postman / curl)

- Register a new client:
  ```bash
  curl -X POST "http://localhost:8080/api/clients" -H "Content-Type: application/json" -d '{"name":"John","email": " john@gmail.com"}
    ```
- Create a membership for the client:
    ```bash
    curl -X POST http://localhost:8080/api/memberships \
      -H "Content-Type: application/json" \
      -d '{
      "clientUserName": "John",
      "type": "GYM_STAR",
      "trainerName": "Alex Trainer",
      "nutritionistName": "Mary Nutri",
      "fitnessClasses": ["Zumba", "Aerobic"]
      }'
    ```

### Testing

#### Unit tests
Run all unit tests with:
```bash
mvn test
```
Included examples: `MembershipServiceTest.java`

#### Integration tests
Use Spring context (H2 + MockMvc):

`FitnessClassControllerIntegrationTest.java`

`FitnessClassRepositoryIntegrationTest.java`

## License
This project was developed for educational and portfolio purposes, to demonstrate Java OOP principles and Spring Boot backend development.


  
