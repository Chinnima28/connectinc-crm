# 🚀 Connectinc CRM: Professional Studio Edition

Connectinc CRM is a robust, full-stack Client Relationship Management system designed as a technical case study in high-performance Java development. It combines a modern, responsive frontend with a strictly-layered, secure backend architecture.

---

## 🏗️ 1. Orchestrated Architecture

This application follows the **Strict Layered Architecture** pattern, ensuring a clean separation of concerns and maximum maintainability.

- **Web Layer (Controller)**: Handles RESTful communication, request validation, and JSON serialization.
- **Business Layer (Service)**: The "brains" of the app. Implements complex business rules and security checks.
- **Data Layer (Repository)**: Leverages Spring Data JPA for efficient, optimized database interactions.

> [!TIP]
> **Why Layers?** By decoupling our logic, we can swap databases or UI frameworks without touching the core business rules. This is how enterprise-scale software is built.

---

## 💻 2. Technical Deep-Dive (The "Educator" Section)

### 🛡️ Row-Level Security
Unlike basic apps, Connectinc implements **Owner-Based Authorization**. Even if a malicious user knows a Client ID, they cannot view or delete it unless they own that record.
```java
// Logic inside CustomerService.java
public void deleteCustomer(Long id, String ownerEmail) {
    // We strictly search by BOTH ID and OwnerEmail
    Customer customer = customerRepository.findByIdAndOwnerEmail(id, ownerEmail)
        .orElseThrow(() -> new RuntimeException("Unauthorized or Not Found"));
    customerRepository.delete(customer);
}
```

### ⚡ Performance & Modern Java
- **Java 21 Records**: Used for Data Transfer Objects (DTOs) like `InteractionRequest` for thread-safety and reduced boilerplate.
- **Sequence Generators**: Optimized for batch inserts, avoiding the performance bottlenecks of standard auto-increment.
- **JPA Method Derivation**: Clean, readable queries like `findAllByOwnerEmail` generated automatically by Spring.

---

## 🧪 3. QA & Testing Excellence

We don't just hope it works—we prove it. Our testing strategy focuses on **Isolation via Mocks**.

### ✅ Automated Unit Testing (JUnit 5 + Mockito)
We follow the **AAA (Arrange-Act-Assert)** pattern to validate our service layer logic without needing a slow, heavy database.

![JUnit Test Execution Result](docs/screenshots/junit_results.png)
<img width="1774" height="574" alt="Screenshot 2026-02-06 163557" src="https://github.com/user-attachments/assets/2bb639f1-e16c-4e04-8cd0-fa34fa0ee5cd" />
<img width="1332" height="272" alt="Screenshot 2026-02-06 164031" src="https://github.com/user-attachments/assets/5908e4cf-7d0d-4eac-8727-0963f049ab59" />



### 🛰️ API testing (Postman)
Comprehensive integration checks to ensure the "pipes" are connected correctly between the frontend and backend.

![Postman API Validation](docs/screenshots/postman_tests.png)
*(Placeholder: Upload your Postman screenshot to `docs/screenshots/postman_tests.png`)*

---

## 🎨 4. Frontend Mastery

The UI is built with **Modern Vanilla JavaScript** and **Vite**, proving that you don't always need heavy frameworks like React to create a premium experience.

- **State Management**: Localized state for responsive UI updates.
- **Dynamic Icons**: Powered by **Lucide**, ensuring a crisp, modern aesthetic.
- **Vibrant CSS**: Custom-built design system with glassmorphism and smooth transitions.

![Frontend Dashboard Walkthrough](docs/screenshots/ui_walkthrough.png)
*(Placeholder: Upload your UI dashboard screenshot to `docs/screenshots/ui_walkthrough.png`)*

---

## 🚀 5. Getting Started (Technical Setup)

### Local Development
1. **Backend**:
   ```bash
   cd chinspring
   ./mvnw spring-boot:run
   ```
2. **Frontend**:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

### Live Deployment
Optimized for **Render.com** using **Docker** and **PostgreSQL**. See [deployment.md](deployment.md) for the full breakdown.

---

## 🎓 Education & Case Study
This project was developed as a comprehensive guide for modern software engineering practices. It demonstrates the bridge between "getting it to work" and "building it to last."
