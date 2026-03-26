# Mahathi Building Contractors - Backend API

A Spring Boot backend for Mahathi Building Contractors website with MySQL database support.

## Features

- Contact form submission with database storage
- Email notifications (optional)
- RESTful API
- MySQL database integration
- WAR/JAR deployment support
- CORS enabled

## Quick Start

### 1. Prerequisites

- Java 17+
- Maven 3.6+
- MySQL 8.0+

### 2. Database Setup

```sql
CREATE DATABASE mahathi_contractor;
```

### 3. Configure Application

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4. Run

```bash
cd backend
mvn spring-boot:run
```

Server runs at: `http://localhost:8080`

---

## Deployment

### Build

```bash
mvn clean package -DskipTests
```

Creates:
- `target/mahathi-contractor-api.war` (Tomcat)
- `target/mahathi-contractor-api.jar` (Standalone)

### Run JAR

```bash
java -jar target/mahathi-contractor-api.jar
```

### Deploy to Tomcat

Copy `mahathi-contractor-api.war` to Tomcat's `webapps/` folder.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/contact` | Submit contact form |
| GET | `/api/submissions` | Get all submissions |
| GET | `/api/submissions/unread` | Get unread |
| GET | `/api/submissions/count` | Get counts |
| PUT | `/api/submissions/{id}/read` | Mark read |
| DELETE | `/api/submissions/{id}` | Delete |
| DELETE | `/api/submissions` | Clear all |
| GET | `/api/health` | Health check |

---

## Test API

```bash
# Health
curl http://localhost:8080/api/health

# Submit form
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@test.com","phone":"9876543210","message":"Hi"}'

# View submissions
curl http://localhost:8080/api/submissions
```

---

## Project Structure

```
backend/
├── pom.xml
├── README.md
├── DEPLOYMENT.md
├── run.bat
└── src/main/
    ├── java/com/mahathi/contractor/
    │   ├── MahathiContractorApplication.java
    │   ├── controller/ContactController.java
    │   ├── entity/ContactSubmission.java
    │   ├── model/
    │   ├── repository/ContactRepository.java
    │   └── service/ContactService.java
    └── resources/
        ├── application.properties
        └── application-prod.properties
```
