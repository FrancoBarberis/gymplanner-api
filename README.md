# gymplanner-api

Proyecto Spring Boot minimal para gestionar usuarios (ejemplo educativo).

## Estado actual (implementado)

- Entidad JPA `User` en `src/main/java/com/franco/gymplanner/users/domain/User.java`.
- Repositorio `UserRepository` en `src/main/java/com/franco/gymplanner/users/repo/UserRepository.java`.
- Service `UserService` en `src/main/java/com/franco/gymplanner/users/service/UserService.java`.
- Controller `UserController` en `src/main/java/com/franco/gymplanner/users/web/UserController.java` con endpoints:
  - `GET /api/v1/users/ping` → test de salud.
  - `POST /api/v1/users/add` → crea usuario y devuelve `201 Created` con DTO (sin password).
- Configuración mínima en `src/main/resources/application.properties`.

## Estructura principal

- `src/main/java/com/franco/gymplanner/users/domain` → entidades (JPA).
- `src/main/java/com/franco/gymplanner/users/repo` → interfaces de acceso a datos (Spring Data JPA).
- `src/main/java/com/franco/gymplanner/users/service` → lógica de negocio.
- `src/main/java/com/franco/gymplanner/users/web` → controllers y DTOs.

## Cómo ejecutar

1. Configura una base de datos (H2, MySQL, Postgres...) y ajusta `application.properties` o `application.yml`.
2. Desde la raíz del proyecto ejecuta:

```bash
./mvnw spring-boot:run
```

(En Windows usa `mvnw.cmd` si no tienes Maven globalmente).

## Probar endpoints

- Salud: `curl -v http://localhost:8080/api/v1/users/ping`
- Crear usuario (ejemplo):

```bash
curl -X POST http://localhost:8080/api/v1/users/add \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"12345678","fullName":"Juan Pérez","role":"USER"}'
```

## Notas y recomendaciones

- Actualmente la contraseña se guarda en claro en el ejemplo; en producción usa `BCryptPasswordEncoder` para hashear contraseñas.
- Manejar errores y respuestas (ej. `@ControllerAdvice`) para devolver mensajes claros al cliente.
- Añadir validaciones adicionales si es necesario y tests unitarios/integración.

## Siguientes pasos sugeridos

- Añadir `src/main/resources/application.yml` con configuración de datasource (o usar H2 para desarrollo).
- Agregar DTOs de respuesta más completos y excepciones custom.
- Crear pruebas unitarias para `UserService`.

---

Este README es editable: modifica o amplia las secciones según avances.
