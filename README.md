# Requests API

Servicio REST para registrar y consultar solicitudes internas de equipamiento, accesos y otros requerimientos.

## Tecnologías
- Java 21, Spring Boot y Spring Data JPA. H2 para desarrollo local.
- Maven, JUnit y MockMvc.
- Arquitectura Controller → Service → Repository con DTOs HTTP.

## Ejecutar

```bash
mvn test
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

El perfil `local` configura una base H2 en memoria, recrea su esquema al iniciar y carga ocho solicitudes de ejemplo. Los datos no persisten entre reinicios.

Sin el perfil `local` no se configura automáticamente una base embebida: para ejecutar el servicio en otro entorno es necesario proporcionar un `DataSource` válido y la configuración de persistencia correspondiente. No hay un perfil de producción predefinido.

## API

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/requests` | Crear una solicitud |
| GET | `/api/requests` | Listar solicitudes |
| GET | `/api/requests/{id}` | Consultar una solicitud |
| GET | `/api/requests/summary` | Resumen de solicitudes por tipo |

La versión actual expone estas operaciones sin autenticación. El comportamiento está especificado en `openspec/specs/requests/spec.md`.

## Documentación
- `openspec/specs/`: requisitos vigentes.
- `openspec/changes/archive/`: cambios históricos.
- `postman/`: colección de pruebas del contrato HTTP y entorno local.
- `CLAUDE.md`: convenciones de desarrollo.

## Desarrollo con OpenSpec

Los comandos y skills de OpenSpec para Claude Code están en `.claude/`.
Se requiere una instalación compatible de la CLI `openspec`.
El proceso de cambios utiliza el schema `openspec/schemas/secured-api/`.

```bash
openspec --version
openspec list
```
