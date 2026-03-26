# Mahathi Building Contractors - Deployment Guide

## Prerequisites

1. **Java 17+** installed
2. **MySQL 8.0+** installed
3. **Maven 3.6+** installed

---

## Step 1: Setup MySQL Database

### Option A: Using MySQL Workbench or CLI

```sql
-- Create database
CREATE DATABASE mahathi_contractor;

-- Create user (optional)
CREATE USER 'mahathi'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON mahathi_contractor.* TO 'mahathi'@'localhost';
FLUSH PRIVILEGES;
```

### Option B: Auto-create

The application will auto-create the database if it doesn't exist.

---

## Step 2: Update Configuration

Edit `src/main/resources/application.properties`:

```properties
# Update with your MySQL credentials
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

## Step 3: Build the Project

```bash
cd backend
mvn clean package -DskipTests
```

This creates:
- `target/mahathi-contractor-api.war` (for Tomcat)
- `target/mahathi-contractor-api.jar` (standalone)

---

## Deployment Options

### Option A: Run as JAR (Recommended for Testing)

```bash
java -jar target/mahathi-contractor-api.jar
```

### Option B: Deploy to Tomcat

1. Copy `mahathi-contractor-api.war` to Tomcat's `webapps/` folder
2. Start Tomcat
3. Access at: `http://localhost:8080/mahathi-contractor-api/api/health`

### Option C: Deploy to Railway/Render/Heroku

Use the JAR file and set environment variables:

```
DATABASE_URL=mysql://user:password@host:3306/mahathi_contractor
MAHATHI_EMAIL_ENABLED=true
SPRING_MAIL_PASSWORD=your_app_password
```

---

## Step 4: Verify Deployment

```bash
# Health check
curl http://localhost:8080/api/health

# Submit test form
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@test.com","phone":"9876543210","message":"Hello"}'

# View submissions
curl http://localhost:8080/api/submissions
```

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/contact` | Submit contact form |
| GET | `/api/submissions` | Get all submissions |
| GET | `/api/submissions/unread` | Get unread submissions |
| GET | `/api/submissions/count` | Get submission counts |
| PUT | `/api/submissions/{id}/read` | Mark as read |
| DELETE | `/api/submissions/{id}` | Delete submission |
| DELETE | `/api/submissions` | Clear all |
| GET | `/api/health` | Health check |

---

## Database Table Structure

The application auto-creates this table:

```sql
CREATE TABLE contact_submissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    service VARCHAR(255),
    location VARCHAR(255),
    message TEXT NOT NULL,
    submitted_at DATETIME,
    is_read BOOLEAN DEFAULT FALSE
);
```

---

## Troubleshooting

### Error: "Unable to connect to database"
- Check MySQL is running
- Verify username/password in application.properties
- Check firewall settings

### Error: "Table doesn't exist"
- Set `spring.jpa.hibernate.ddl-auto=update`
- Database will auto-create tables

### Error: "Email authentication failed"
- Generate new app password from Google
- Update `spring.mail.password`

---

## Production Checklist

- [ ] Change default MySQL password
- [ ] Enable HTTPS
- [ ] Set `spring.jpa.hibernate.ddl-auto=validate` (for production)
- [ ] Configure email properly
- [ ] Set up proper CORS origins
- [ ] Enable logging for errors
- [ ] Set up database backups
