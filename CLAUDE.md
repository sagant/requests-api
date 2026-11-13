# Convenciones de Requests API

## Interacción
- Utilizar `AskUserQuestion`, cuando esté disponible, para aclarar decisiones de negocio, permisos o decisiones arquitectónicas relevantes con varias alternativas. Si no está disponible, preguntar en la conversación.
- No asumir decisiones que alteren el comportamiento acordado. Resolver los detalles ordinarios de implementación siguiendo las convenciones del proyecto.

## Arquitectura
- Java 21, Spring Boot y Maven. H2 se utiliza para desarrollo local.
- Arquitectura Controller → Service → Repository.
- Separar DTOs HTTP de entidades JPA y mantener la lógica de negocio fuera de los controllers.
- Antes de modificar una funcionalidad, consultar el código, las pruebas y las especificaciones vigentes que puedan verse afectadas.

## Gestión de cambios
- Los cambios de comportamiento se especifican mediante changes de OpenSpec y requieren revisión humana antes de implementarse.
- No modificar directamente las specs vigentes durante la elaboración de un change.
- Cuando cambie el contrato HTTP, actualizar la colección versionada `postman/requests-api.postman_collection.json`, incluidos ejemplos y aserciones.

## Seguridad
- Los endpoints de negocio nuevos o modificados bajo `/api/**` deben utilizar Spring Security HTTP Basic.
- Utilizar Spring Security para la autenticación; no procesar manualmente las cabeceras Basic.
- Obtener la identidad del usuario desde el contexto autenticado de Spring Security y no de datos suministrados por el cliente.
- Aplicar las reglas de autorización definidas en las especificaciones aprobadas.
- Si una funcionalidad existente no cumple la política de autenticación, especificar expresamente su adecuación en el change que la afecte.

## Pruebas y validación
- Derivar pruebas positivas, negativas y de regresión de los Scenarios de las especificaciones.
- Ejecutar `mvn test` y, si cambia el contrato HTTP, verificar la colección Postman.
- Revisar el diff e informar qué comprobaciones se ejecutaron, sus resultados y las que quedaron pendientes.
- No incorporar secretos ni credenciales reales al repositorio.
