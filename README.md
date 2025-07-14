# 📦 Proyecto Spring Boot con Spring Security y JWT

Este proyecto es una API REST desarrollada con Spring Boot que 
implementa autenticación utilizando **JWT (JSON Web Token)** 
y **Spring Security**.

---

## 🔧 Tecnologías

- Java 17
- Spring Boot 3.5.3
- Spring Security
- JWT
- Spring Web
- Spring Data JPA
- H2 
- Lombok
- Gradle
- Swagger

---

## 🛠️ Funcionalidades

- Registro de usuarios
- Inicio de sesión con generación de token JWT
- Endpoints protegidos
- Base de datos en memoria

---

## 🔐 Seguridad

- Hash de contraseñas con BCrypt
- Autenticación basada en JWT
- Filtro personalizado para validar el token en cada request

---

##  📷 Diagrama del proyecto
- https://gitlab.com/ocardenasmartinez/nisum-test/-/blob/28c5b2bc013d8b489e819cab6581be6ccdec56bc/solution-diagram.jpg
---

## 🔧 Configuración

### application.properties

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

validation.email=^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$
validation.password=.*_.*
```

---

## 🛠️ Endpoints

| Método | Ruta            | Seguridad    | Descripción               |
| ------ | --------------- | ------------ | ------------------------- |
| POST   | `/api/auth/signup` | Pública      | Registro de nuevo usuario |
| POST   | `/api/auth/login`   | Pública      | Autenticación y token JWT |

---

## 📥 Clonar el repositorio

```bash
git clone git@gitlab.com:ocardenasmartinez/nisum-test.git
cd nisum-test
```
---

## 🧪 Pruebas
  La aplicación incluye pruebas unitarias:

- JUnit 5
- Mockito
- Spring Boot Test

```bash
./gradlew test
```
---

## 🔄 Cómo Ejecutar
Desde línea de comandos

```bash
./gradlew bootRun 
```
---

## 📚 Documentación Swagger
Disponible en:
- http://localhost:8080/swagger-ui.html
---

## 🧪 Probar la API con `curl`

### 1. Registro de usuario

```bash
curl --location 'localhost:8080/api/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "juan",
    "email": "juan@gmail.com",
    "password": "123_",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        },
        {
            "number": "3243252",
            "cityCode": "5",
            "countryCode": "67"
        }
    ]
}'
```

### 2. Login de usuario

```bash
curl --location 'localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "juan@gmail.com",
    "password": "123_"
}'
```

---

## 🙋 Autor
- Octavio Cárdenas
- Software Engineer
- 📧 ocardenasmartinez@gmail.com
---

