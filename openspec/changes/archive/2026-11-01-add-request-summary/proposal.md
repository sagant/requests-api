# Propuesta: resumen de solicitudes por tipo

## Why
El área de operaciones necesita conocer rápidamente cuántas solicitudes
existen en el buzón compartido, agrupadas por tipo, sin descargar el listado completo.

## What Changes
- Incorporar `GET /api/requests/summary`.
- Devolver el total y un conteo por cada tipo (`EQUIPMENT`, `ACCESS`, `OTHER`).
- Conservar sin modificaciones las tres operaciones existentes.
- Añadir pruebas de integración para el resumen y ejemplos HTTP a la colección Postman.

## Impacto
Se agrega una operación de solo lectura sobre el repositorio actual.
No se introduce autenticación ni cambia la visibilidad global del buzón existente.
