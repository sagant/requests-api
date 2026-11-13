# Colección Postman — Requests API

La colección contiene ejemplos de solicitudes y aserciones para el contrato HTTP vigente.

## Uso
1. Iniciar el servicio con `mvn spring-boot:run -Dspring-boot.run.profiles=local`.
2. Importar `requests-api.postman_collection.json` y `local.postman_environment.json`.
3. Seleccionar el entorno local (`baseUrl=http://localhost:8080`).
4. Ejecutar las peticiones en orden. La petición de creación guarda `createdRequestId` para consultas posteriores.
5. Revisar los códigos HTTP, respuestas y aserciones `pm.test`.

## Mantenimiento
Al cambiar el contrato HTTP, actualizar las peticiones y las aserciones de esta colección
para reflejar las especificaciones vigentes y los cambios aprobados.
No incorporar contraseñas, tokens ni secretos a los JSON versionados.
