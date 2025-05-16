

# 🎓 E-Learning Backend - Spring Boot 3 Application

This is the backend for the **E-Learning Application**, built using **Spring Boot 3**, **Java 17**, **Spring Security with JWT**, and **MySQL**.

## 🔗 Frontend Repository

👉 [E-Learning Frontend (Angular)](https://github.com/AliSobih/Aklemy-FrontEnd)

## 📚 Description

The application provides an online learning platform with user management and course interaction. There are three roles:

### 🧑‍🎓 Student
- Enroll in courses
- Watch lessons
- Take exams for:
  - Each lesson
  - Each section
  - The entire course

### 👨‍🏫 Teacher
- Add, update, and delete **their own** courses
- Create and manage exams and questions in their courses
- Approve enrollments of students in **their own** courses

### 🛠️ Admin
- Full control over the system
- Manage categories
- Add, update, and delete:
  - Courses
  - Exams
  - Questions
- Approve:
  - Student enrollments
  - Teachers

## 🧰 Technologies Used

- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication
- MySQL
- JPA/Hibernate

## 🖼️ Screenshots

> Add the images in a folder like `docs/images` and link them here.

### 📌 Example Images

| Image | Description |
|-------|-------------|
| ![image](https://github.com/user-attachments/assets/61d6b005-1f98-435c-a687-8bbe85ebb485) | Login screen with JWT authentication |
| ![image](https://github.com/user-attachments/assets/786ff542-0148-4e4b-905a-6c76e0368778) | Admin dashboard for managing categories, users, and courses |
| ![image](https://github.com/user-attachments/assets/c0580c97-3174-4cfc-a42c-ec3dba3f8349) | Course content view for enrolled students |
| ![image](https://github.com/user-attachments/assets/72e3565f-8f41-4cd2-b819-35503f5f5f30)| Exam interface with multiple-choice questions |

---

## 🚀 Running the Backend Locally

1. Clone the repository
2. Configure `application.properties` or `application.yml` for your MySQL DB
3. Run using your IDE or `./mvnw spring-boot:run`

---

## 📂 Project Structure
```
  src/
  ├── config # Security and JWT configuration
  ├── controllers # REST endpoints
  ├── services # Business logic
  ├── models # JPA entities
  ├── repositories # Spring Data JPA repositories
  └── dtos # Data transfer objects

```
