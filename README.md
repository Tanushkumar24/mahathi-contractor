# Mahathi Building Contractors - Complete Project

A professional construction company website with Spring Boot backend, MySQL database, and Docker support.

## Project Structure

```
mahathi-contractors/
├── index.html                      # Frontend website
├── Dockerfile                      # Docker deployment
├── docker-compose.yml              # Local development
├── docker.bat                     # Docker helper script
├── .dockerignore                  # Docker ignore file
├── RAILWAY_DEPLOYMENT.md          # Railway deployment guide
├── backend/
│   ├── pom.xml
│   ├── src/main/java/com/mahathi/contractor/
│   │   ├── MahathiContractorApplication.java
│   │   ├── controller/ContactController.java
│   │   ├── entity/ContactSubmission.java
│   │   ├── model/
│   │   ├── repository/ContactRepository.java
│   │   └── service/ContactService.java
│   └── src/main/resources/
│       ├── application.properties      # Local config
│       └── application-prod.properties # Production config
```

---

## Quick Start

### Option 1: Docker (Recommended)

```bash
# Start all services (Backend + MySQL)
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

**Access:**
- Backend API: http://localhost:8080
- Health Check: http://localhost:8080/api/health

---

### Option 2: Local Development

```bash
cd backend
mvn spring-boot:run
```

**Requires:** Java 17+, MySQL running locally

---

## Deployment

### Railway (Recommended for Backend)

1. Create Railway account at https://railway.app
2. Add MySQL database
3. Deploy from GitHub
4. Set environment variables
5. See `RAILWAY_DEPLOYMENT.md` for details

### Vercel (Frontend)

1. Create Vercel account
2. Import project
3. Deploy

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
# Health check
curl http://localhost:8080/api/health

# Submit form
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "9876543210",
    "service": "House Construction",
    "location": "Vijayawada",
    "message": "I want to build a 3BHK house"
  }'

# View submissions
curl http://localhost:8080/api/submissions
```

---

## Features

- Modern responsive frontend
- Contact form with validation
- MySQL database storage
- Email notifications (optional)
- Docker deployment
- Railway deployment ready
- CORS enabled
- RESTful API

---

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| PORT | Server port | 8080 |
| DB_HOST | MySQL host | localhost |
| DB_PORT | MySQL port | 3306 |
| DB_NAME | Database name | mahathi_contractor |
| DB_USERNAME | DB user | root |
| DB_PASSWORD | DB password | - |
| EMAIL_ENABLED | Enable email | false |
| CORS_ORIGINS | Allowed origins | * |

---

## Tech Stack

- **Frontend:** HTML5, CSS3, JavaScript, Font Awesome
- **Backend:** Spring Boot 3.2, Java 17
- **Database:** MySQL 8.0
- **Deployment:** Docker, Railway, Vercel
