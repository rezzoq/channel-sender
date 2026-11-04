# channel-sender

Multi-tenant notification delivery platform (webhook, email, Telegram).

**Stack:** Java 21, Spring Boot, Kafka, PostgreSQL, MongoDB, Redis, Kubernetes.

**Status:** Work in progress.

## Run locally

**Requirements:** JDK 21, Docker

### 1. Configure environment

Linux / macOS:
```bash
cp .env.example .env
```

Windows (PowerShell):
```powershell
Copy-Item .env.example .env
```

### 2. Start infrastructure

```bash
docker compose up -d
```

### 3. Run the API service

Windows:
```powershell
.\gradlew :api-service:bootRun
```

Linux / macOS:
```bash
./gradlew :api-service:bootRun
```

### 4. Check application health

http://localhost:8080/actuator/health

### Service ports

| Service    | Port  |
|------------|-------|
| PostgreSQL | 5432  |
| MongoDB    | 27017 |
| Redis      | 6379  |
| Kafka      | 29092 |