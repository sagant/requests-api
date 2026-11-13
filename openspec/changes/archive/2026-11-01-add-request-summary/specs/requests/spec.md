## ADDED Requirements

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
