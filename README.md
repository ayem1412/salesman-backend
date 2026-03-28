# SalesMaster

**Author:** EL MAHDAD ANAS  
**Supervisor:** Mme. HONNIT BOUCHRA

---

## Tech Stack
* **Language:** Java 25
* **Framework:** Spring Boot 4.x
* **Database:** PostgreSQL
* **Security:** Spring Security (Identity Service)
* **Service Discovery:** Netflix Eureka
* **API Gateway:** Spring Cloud Gateway
* **Communication:** OpenFeign
* **Invoicing:** OpenPDF

---

## Project Structure

```text
salesmaster-backend/
 ├── collections/           # Postman test collections & environments
 ├── discovery-server/      # Eureka Server (Port 8761)
 ├── api-gateway/           # Spring Cloud API Gateway (Port 8080)
 ├── identity-service/      # User authentication and Role management
 ├── customer-service/      # Customer profiles and history
 ├── inventory-service/     # Product catalog and stock levels
 └── order-service/         # Sales orchestration & OpenPDF invoice generation
```

## Infrastructure Services

```bash
./mvnw spring-boot:run -pl discovery-server
```

```bash
./mvnw spring-boot:run -pl api-gateway
```

## Security & Identity

```bash
./mvnw spring-boot:run -pl identity-service
```

## Business Microservices

```bash
./mvnw spring-boot:run -pl customer-service
```

```bash
./mvnw spring-boot:run -pl inventory-service
```

```bash
./mvnw spring-boot:run -pl order-service
```
