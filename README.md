# ⚙️ Energy Tracker – Backend

**Energy Tracker** is the backend of a complete energy data management platform developed as the **Final Degree Project (TFG)** of **Óscar González Tur** (BSc. in Computer Engineering).  
The system was designed for **Leipzig Airport**, addressing the need to modernize its energy monitoring infrastructure and enable efficient, reliable, and sustainable energy management.

---

## 🌍 Project Overview

The purpose of this project is to develop a **web application and RESTful API** for querying and analysing the 15-minute load profiles of electric meters at Leipzig Airport.  
This solution improves data quality, facilitates detailed analysis, and helps energy managers identify consumption patterns, inefficiencies, and opportunities for optimization — in full alignment with **ISO 50001** standards.

The backend was developed with **Java and Spring Boot**, while the frontend was built with **React.js and Material-UI**, ensuring a **robust, scalable, and modular architecture**.  
A **PostgreSQL** database stores validated load profiles, ensuring integrity and reliability through automatic validation and correction mechanisms.

---

## 🧠 Architecture

The platform is built on a **microservice architecture** with a **Hexagonal (Ports and Adapters) design**, promoting independence between the business logic and external interfaces.

### 🔧 Main Microservices
- **AuthService** → Authentication and role-based access using JWT.
- **UserService** → User management and role assignment.
- **DeviceCatalog** → Registration and configuration of energy meters, stations, and metering points.
- **DataCollector** → Real-time data collection via Ethernet/RS232 using the proprietary **ECL (Energy Control Language)** protocol.
- **ConsumptionService** → Storage, analysis, and CSV export of consumption data.
- **RabbitMQ** → Asynchronous messaging for decoupled service communication.

---

## 🧱 Technologies & Tools

| Category | Technologies |
|-----------|---------------|
| Language | Java 17 |
| Framework | Spring Boot, Spring Data JPA, Spring Security |
| Architecture | Hexagonal, Clean Architecture, Microservices |
| Database | PostgreSQL, JSON persistence (DataCollector) |
| Messaging | RabbitMQ |
| CI/CD | GitHub Actions, Docker |
| Testing | JUnit 5, Mockito |
| Documentation | Swagger / OpenAPI |
| Version Control | Git + GitHub |

---

## 📈 Key Features

- RESTful API documented with Swagger  
- Secure authentication with JWT  
- Asynchronous communication with RabbitMQ  
- Data validation and correction logic  
- CSV export for energy consumption reports  
- Modular and extensible clean architecture  
- Continuous Integration/Deployment with GitHub Actions  
- Deployed demo environment on Hostinger (Docker)

---

## ⚙️ Local Setup

### Requirements
- Java 17  
- Docker & Docker Compose  
- Running instances of RabbitMQ and PostgreSQL  

### Installation
```bash
git clone https://github.com/Krontur/energytracker.git
cd energytracker
./build_and_run.sh
```

## 📜 License

This work is licensed under the **[Creative Commons BY-NC-ND 3.0 Spain](http://creativecommons.org/licenses/by-nc-nd/3.0/es/)** license.  
© 2025 **Óscar González Tur**

