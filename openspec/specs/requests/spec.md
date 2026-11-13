# Solicitudes internas

## Purpose
Administrar un buzón compartido de solicitudes internas mediante una API REST. No existe autenticación, autorización, identidad de solicitante ni flujo de aprobación en el contrato vigente.

## Requirements

### Requirement: Registrar solicitudes
El sistema SHALL permitir que cualquier cliente HTTP cree una solicitud mediante `POST /api/requests`, proporcionando título, descripción y tipo válido (`EQUIPMENT`, `ACCESS`, `OTHER`). La respuesta incluirá el ID generado y la fecha de creación.

#### Scenario: Crear solicitud válida
- **GIVEN** un título, descripción y tipo válidos
- **WHEN** el cliente invoca `POST /api/requests`
- **THEN** el sistema guarda la solicitud y responde `201 Created` con su identificador

#### Scenario: Rechazar solicitud inválida
- **GIVEN** un título vacío o una descripción demasiado corta
- **WHEN** el cliente invoca `POST /api/requests`
- **THEN** el sistema responde `400 Bad Request`

### Requirement: Consultar listado completo
El sistema SHALL devolver a cualquier cliente HTTP todas las solicitudes existentes mediante `GET /api/requests`, ordenadas por identificador ascendente y sin aplicar filtros de usuario.

#### Scenario: Listar las solicitudes
- **GIVEN** que existen solicitudes registradas
- **WHEN** cualquier cliente invoca `GET /api/requests`
- **THEN** el sistema responde `200 OK` con todas las solicitudes

### Requirement: Consultar detalle
El sistema SHALL permitir que cualquier cliente HTTP consulte el detalle de una solicitud mediante `GET /api/requests/{id}`, sin aplicar controles de propiedad.

#### Scenario: Consultar solicitud existente
- **GIVEN** una solicitud existente
- **WHEN** cualquier cliente invoca `GET /api/requests/{id}`
- **THEN** el sistema responde `200 OK` con sus datos

#### Scenario: Solicitud inexistente
- **GIVEN** un identificador que no existe
- **WHEN** el cliente invoca `GET /api/requests/{id}`
- **THEN** el sistema responde `404 Not Found`

### Requirement: Consultar resumen global
El sistema SHALL permitir que cualquier cliente HTTP consulte `GET /api/requests/summary`
para obtener la cantidad total de solicitudes y las cantidades por tipo, sin filtrar por usuario.
El mapa `byType` incluye los tres tipos, incluso cuando la cantidad sea cero.

#### Scenario: Resumen por tipo
- **GIVEN** dos solicitudes `EQUIPMENT` y una `ACCESS`
- **WHEN** cualquier cliente invoca `GET /api/requests/summary`
- **THEN** el sistema responde `200 OK` con `total=3`, `byType.EQUIPMENT=2`, `byType.ACCESS=1` y `byType.OTHER=0`

#### Scenario: Resumen vacío
- **GIVEN** que no hay solicitudes
- **WHEN** cualquier cliente invoca `GET /api/requests/summary`
- **THEN** el sistema responde `200 OK` con un total de cero y tres cantidades en cero
