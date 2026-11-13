# Tareas del cambio

## Implementación
- [ ] Implementar los requisitos aprobados sin ampliar el alcance.
- [ ] Adaptar los componentes existentes afectados por requisitos MODIFIED.

## Trazabilidad de Scenarios y pruebas
<!-- Registrar una fila por cada Scenario del delta, con al menos una prueba automatizada prevista. -->
| Capacidad / Scenario | Prueba prevista (archivo y método o petición Postman con aserción) |
|---|---|
| <capacidad / Scenario> | <prueba identificable> |

## Pruebas y regresión
- [ ] Implementar las pruebas previstas para todos los Scenarios, incluidos casos negativos y de borde aplicables.
- [ ] Revisar la regresión de comportamientos existentes y ajustar tests previos solo cuando el contrato aprobado lo requiera.
- [ ] Ejecutar `mvn test` y revisar el resultado.

## Contrato HTTP y Postman (si aplica)
- [ ] Actualizar peticiones, autenticación, payloads, respuestas, errores y aserciones de la colección versionada que hayan cambiado.
- [ ] Ejecutar las peticiones afectadas de Postman y revisar sus resultados sin incorporar secretos al repositorio.

## Cierre
- [ ] Comprobar que cada Scenario se corresponde con al menos una prueba implementada y ejecutable.
- [ ] Inspeccionar el diff y contrastar el resultado con la propuesta aprobada.
- [ ] Registrar el resultado de las comprobaciones y las que no se pudieron ejecutar.
