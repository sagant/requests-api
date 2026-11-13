# Diseño: resumen de solicitudes

## Contexto
El sistema ya ofrece listado y detalle de solicitudes desde `RequestService` y `RequestRepository`.
Se conserva ese diseño por capas.

## Decisión
Agregar una operación de solo lectura en `RequestController` y un método `summary()`
en `RequestService`, que agrupa solicitudes por `RequestType`.
Definir un `RequestSummaryDto` separado de la entidad JPA.

## Alternativas
Dado el volumen esperado de solicitudes, se utiliza agregación en memoria.
Una consulta de agregación en SQL sería apropiada para volúmenes mayores.

## Testing y contrato HTTP
Verificar conteos por tipo, respuesta vacía y ausencia de regresión en rutas anteriores.
Registrar ejemplos HTTP reproducibles en la colección Postman.
