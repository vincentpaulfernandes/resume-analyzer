# 🚀 AI Resume Analyzer

An AI-powered backend application that analyzes resumes and provides structured insights like skills, experience level, strengths, suggestions, and job-fit score.

---

## 📌 Overview

This project allows users to upload a resume (PDF), extracts the content, and uses AI to analyze and return meaningful insights. The results are stored in a PostgreSQL database for future retrieval.

---

## 🔥 Features

* Upload PDF resume
* Extract text using Apache PDFBox
* AI-powered analysis (Gemini API)
* Returns:

    * Skills
    * Experience Level
    * Strengths
    * Suggestions
    * Job Fit Score
* Stores results in PostgreSQL (JSONB)
* REST APIs for accessing data
* Swagger UI for API testing
* Dockerized application

---

## 🛠 Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL (JSONB)
* Docker & Docker Compose
* Swagger (OpenAPI)
* Apache PDFBox
* Gemini API

---

## ⚙️ API Endpoints

| Method | Endpoint             | Description               |
| ------ | -------------------- | ------------------------- |
| POST   | `/api/resume/upload` | Upload resume and analyze |
| GET    | `/api/resume/latest` | Get latest analysis       |
| GET    | `/api/resume/all`    | Get all analyses          |

---

## 🧪 Sample Response

```json
{
  "experience_level": "Entry-Level",
  "job_fit_score": 85,
  "skills": ["Java", "Spring Boot", "Kafka"],
  "strengths": ["Strong backend development skills"],
  "suggestions": ["Add quantified achievements in resume"]
}
```

---

## 🐳 Run with Docker

### 1. Clone the repository

```bash
git clone https://github.com/vincentpaulfernandes/resume-analyzer.git
cd resume-analyzer
```

### 2. Create `.env` file

```env
POSTGRES_DB=resume_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=yourpassword
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=yourpassword
GEMINI_API_KEY=your_api_key
```

### 3. Build and run

```bash
docker compose up --build
```

### 4. Access Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 📂 Project Structure

```
controller  → REST APIs
service     → Business logic
repository  → Database layer
entity      → Database models
dto         → Request/Response models
config      → Configuration classes
```

---

## 💡 Future Improvements

* JWT Authentication & Security
* Kafka Event Streaming
* Frontend UI (React)
* CI/CD Pipeline

---

## 👨‍💻 Author

Vincent Paul Fernandes
