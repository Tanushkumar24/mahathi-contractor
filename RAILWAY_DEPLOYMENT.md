# Railway Deployment Guide - Mahathi Building Contractors

## Complete Step-by-Step Deployment

---

## Step 1: Prepare Railway Account & Database

### 1.1 Create Railway Account
1. Go to: https://railway.app
2. Sign up with GitHub
3. Verify email

### 1.2 Create MySQL Database
1. Click **"New Project"** → **"Database"** → **"MySQL"**
2. Wait for database to provision
3. Click on the database → **"Variables"** tab
4. Copy these variables (you'll need them later):
   - `MYSQLHOST` (or `DB_HOST`)
   - `MYSQLPORT` (usually `3306`)
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`

### 1.3 Create Spring Boot Project
1. Click **"New Project"** → **"Deploy from GitHub repo"**
2. Select your repository
3. Railway will auto-detect Dockerfile

---

## Step 2: Configure Environment Variables

Go to your Spring Boot project on Railway → **Settings** → **Variables**

Add these variables:

```
# ===========================================
# DATABASE (from Railway MySQL)
# ===========================================
DB_HOST=your-mysql-host.railway.app
DB_PORT=3306
DB_NAME=railway
DB_USERNAME=root
DB_PASSWORD=your-db-password

# ===========================================
# EMAIL (Optional - for notifications)
# ===========================================
EMAIL_ENABLED=false
SPRING_MAIL_USERNAME=mahathicontractor@gmail.com
SPRING_MAIL_PASSWORD=your-gmail-app-password

# ===========================================
# CORS (Your Vercel frontend URL)
# ===========================================
CORS_ORIGINS=https://your-frontend.vercel.app

# ===========================================
# LOGGING
# ===========================================
LOG_LEVEL=INFO
```

---

## Step 3: Verify Dockerfile

Your Dockerfile should be at the root: `Dockerfile`

```dockerfile
# ===========================================
# STAGE 1: Build
# ===========================================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY backend/pom.xml backend/
RUN mvn -f backend/pom.xml dependency:go-offline -B

COPY backend/src backend/
RUN mvn -f backend/pom.xml package -DskipTests -B

# ===========================================
# STAGE 2: Runtime
# ===========================================
FROM eclipse-temurin:17-jre

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR /app

COPY --from=builder /app/backend/target/mahathi-contractor-api.jar app.jar

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx512m", "-XX:+UseG1GC", "app.jar"]
```

---

## Step 4: Deploy

1. Click **"Deploy"** on Railway
2. Watch the logs for build progress
3. Wait 3-5 minutes for first deploy

### If Build Fails:

Check logs for specific errors. Common issues:

| Error | Solution |
|-------|----------|
| `pom.xml not found` | Move Dockerfile to root, verify path in COPY |
| `mvn not found` | Use multi-stage build with maven image |
| `jar not found` | Check `finalName` in pom.xml matches |

---

## Step 5: Verify Deployment

### 5.1 Health Check
```bash
curl https://your-backend.railway.app/api/health
```

Expected response:
```json
{
  "success": true,
  "message": "Server is running",
  "data": {
    "service": "Mahathi Building Contractors API",
    "version": "1.0.0",
    "status": "active",
    "database": "MySQL Connected"
  }
}
```

### 5.2 Test Contact Form
```bash
curl -X POST https://your-backend.railway.app/api/contact \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "phone": "9876543210",
    "service": "House Construction",
    "location": "Vijayawada",
    "message": "Testing deployment"
  }'
```

Expected response:
```json
{
  "success": true,
  "message": "Thank you for your inquiry! We will contact you within 24 hours.",
  "data": {
    "id": 1,
    "name": "Test User",
    ...
  }
}
```

### 5.3 View Submissions
```bash
curl https://your-backend.railway.app/api/submissions
```

---

## Step 6: Connect Frontend to Backend

Update your frontend `index.html`:

```javascript
// Replace with your Railway backend URL
const API_BASE_URL = 'https://your-backend.railway.app/api';
```

Deploy your frontend to Vercel with the same CORS_ORIGINS setting.

---

## Step 7: Custom Domain (Optional)

### Railway Backend
1. Project Settings → Networking → Add Domain
2. Add your subdomain: `api.mahathicontractor.com`

### Vercel Frontend
1. Project Settings → Domains
2. Add: `mahathicontractor.com`

### Update CORS
```
CORS_ORIGINS=https://mahathicontractor.com,https://www.mahathicontractor.com
```

---

## Troubleshooting

### Database Connection Failed
```bash
# Test MySQL connection from Railway
telnet your-mysql-host.railway.app 3306
```

Fix: Ensure DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD are correct

### CORS Error
```
Access to fetch at 'https://backend.railway.app' from origin 'https://your-site.com' 
has been blocked by CORS policy
```

Fix: Update `CORS_ORIGINS` in Railway variables

### Health Check Failing
```bash
# Check if app is running
curl http://localhost:8080/api/health
```

Check Railway logs for startup errors

### Out of Memory
Reduce JVM heap in Dockerfile:
```dockerfile
ENTRYPOINT ["java", "-jar", "-Xms128m", "-Xmx256m", "app.jar"]
```

---

## Useful Railway Commands

```bash
# View logs
railway logs -f

# Open shell
railway run bash

# Check environment
railway variables

# Redeploy
railway up
```

---

## Production Checklist

- [ ] MySQL database created on Railway
- [ ] Environment variables configured
- [ ] Dockerfile at repository root
- [ ] Health check passing
- [ ] Contact form working
- [ ] CORS configured for frontend
- [ ] Custom domain set (optional)
- [ ] HTTPS enabled (automatic on Railway)
- [ ] Monitoring/logs configured
