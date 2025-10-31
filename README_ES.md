## 🇪🇸 **README_ES.md (Versión en español)**

# ⚙️ Energy Tracker – Backend

**Energy Tracker** es el backend de una plataforma de gestión de datos energéticos desarrollada como **Trabajo de Fin de Grado en Ingeniería Informática** por **Óscar González Tur**.  
El sistema fue diseñado para el **Aeropuerto de Leipzig**, con el objetivo de modernizar su infraestructura de monitorización energética y optimizar la eficiencia y sostenibilidad operativa.

---

## 🌍 Descripción del proyecto

El objetivo de este proyecto es desarrollar una **aplicación web y una API REST** para consultar y analizar los perfiles de carga de los contadores eléctricos en intervalos de 15 minutos.  
La solución mejora la calidad de los datos, permite detectar patrones de consumo y facilita la toma de decisiones informadas para reducir costes y optimizar la eficiencia energética, cumpliendo con los estándares de la norma **ISO 50001**.

El backend está desarrollado con **Java y Spring Boot**, mientras que el frontend utiliza **React.js y Material-UI**, garantizando una arquitectura **robusta, escalable y modular**.  
La base de datos **PostgreSQL** almacena los perfiles de carga validados, aplicando mecanismos de corrección automática para mantener la integridad de los datos.

---

## 🧠 Arquitectura

La plataforma está basada en una **arquitectura de microservicios** y un modelo **hexagonal (puertos y adaptadores)**, que desacopla la lógica de negocio de las dependencias externas y facilita el mantenimiento.

### 🔧 Microservicios principales
- **AuthService** → Autenticación y control de acceso basado en roles con JWT.  
- **UserService** → Gestión de usuarios y asignación de permisos.  
- **DeviceCatalog** → Inventario y administración de estaciones, canales y contadores.  
- **DataCollector** → Recolección de datos en tiempo real mediante Ethernet/RS232 usando el protocolo propietario **ECL (Energy Control Language)**.  
- **ConsumptionService** → Almacenamiento, análisis y exportación de consumos en CSV.  
- **RabbitMQ** → Comunicación asíncrona entre microservicios.

---

## 🧱 Tecnologías utilizadas

| Categoría | Tecnologías |
|------------|-------------|
| Lenguaje | Java 17 |
| Framework | Spring Boot, Spring Data JPA, Spring Security |
| Arquitectura | Hexagonal, Clean Architecture, Microservicios |
| Base de datos | PostgreSQL, persistencia JSON (DataCollector) |
| Mensajería | RabbitMQ |
| CI/CD | GitHub Actions, Docker |
| Pruebas | JUnit 5, Mockito |
| Documentación | Swagger / OpenAPI |
| Control de versiones | Git + GitHub |

---

## 📈 Funcionalidades principales

- API REST documentada con Swagger  
- Autenticación segura con JWT  
- Comunicación asíncrona con RabbitMQ  
- Validación y corrección automática de datos  
- Exportación de informes en formato CSV  
- Arquitectura modular y extensible  
- Integración y despliegue continuos con GitHub Actions  
- Entorno de demostración desplegado en Hostinger (Docker)

---

## ⚙️ Despliegue local

### Requisitos
- Java 17  
- Docker y Docker Compose  
- RabbitMQ y PostgreSQL en ejecución  

### Instalación
```bash
git clone https://github.com/Krontur/energytracker.git
cd energytracker
./build_and_run.sh
```

---

## 📜 Licencia

Este trabajo está protegido bajo la licencia **[Creative Commons BY-NC-ND 3.0 España](http://creativecommons.org/licenses/by-nc-nd/3.0/es/)**.  
© 2025 **Óscar González Tur**

