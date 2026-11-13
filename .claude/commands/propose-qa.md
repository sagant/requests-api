---
description: Ejecuta el propose de OpenSpec y luego una revisión QA con el subagente qa-reviewer, aplicando solamente correcciones mecánicas. 
argument-hint: <descripción del cambio o nombre del change>
disable-model-invocation: true
---

# Propose con revisión QA

Pedido del usuario: $ARGUMENTS

Seguir las fases en orden y respetar `CLAUDE.md`. No modificar código funcional; finalizar con los artefactos pendientes de revisión humana.

## Fase 1 — Propose de OpenSpec
1. Si no hay pedido, preguntar qué se quiere proponer y detenerse.
2. Si en la conversación hubo una exploración previa (p. ej. `/opsx:explore`), usar sus decisiones, supuestos y alcance como entrada del propose, y registrarlos explícitamente en `proposal.md` / `design.md` para que el `qa-reviewer` pueda evaluarlos sin el historial.
3. Invocar el workflow/skill instalado de OpenSpec (`/opsx:propose` o `openspec-propose` según el entorno). No reinventar su lógica.
4. Si no hay mecanismo OpenSpec disponible para generar artefactos, detenerse e indicar que debe instalarse/actualizarse la integración de OpenSpec.
5. Obtener el nombre real y la ruta `openspec/changes/<change>/` del cambio creado.

## Fase 2 — Validación estructural
Si existe la CLI, ejecutar `openspec validate <change> --strict`, corregir fallos de forma sin cambiar decisiones de negocio; si no está disponible, advertirlo en el informe.

## Fase 3 — Revisión QA
Invocar explícitamente el subagente `qa-reviewer` con un prompt autocontenido (no asumir que recibe todo el historial) que incluya:
- Nombre y ruta del change, con `proposal.md`, `design.md`, `tasks.md`, `specs/**` y `security-review.md` cuando exista.
- Specs vigentes afectadas y tests de `src/test/**`.
- Colección `postman/requests-api.postman_collection.json`.
- Políticas de `CLAUDE.md`, especialmente seguridad en `/api/**`.
- Trabajar con los contratos y la documentación del repositorio; evitar acceder a secretos.
- Pedir hallazgos `CRÍTICO`, `IMPORTANTE`, `OPCIONAL` con referencias `archivo:línea`.

## Fase 4 — Verificar hallazgos
Verificar contra el archivo citado cada hallazgo crítico/importante. Descartar los no sustentados. Distinguir:
- **Corrección mecánica:** se desprende de requisitos o políticas ya acordados, sin decidir un comportamiento nuevo (formato, test/Postman faltante, contradicción documental).
- **Decisión pendiente:** afecta comportamiento de negocio, roles, permisos, respuesta HTTP o alcance aún no especificado.

## Fase 5 — Corregir sin implementar
- Aplicar **solo correcciones mecánicas** dentro de `openspec/changes/<change>/`.
- Registrar decisiones pendientes en `## Preguntas abiertas` de `proposal.md` o `security-review.md`, sin elegir una opción.
- No editar `openspec/specs/`, `src/**`, Postman ni configuración; no marcar artefactos como aprobados.
- Repetir la validación estructural si se modificaron artefactos.

## Fase 6 — Segunda revisión (a lo sumo una)
Si se corrigieron hallazgos CRÍTICOS, invocar otra vez a `qa-reviewer` una sola vez. Los nuevos hallazgos no se corrigen automáticamente: se informan. Máximo dos pasadas en total.

## Fase 7 — Informe y compuerta humana
Responder en español:
1. Nombre de change y artefactos creados o modificados.
2. Tabla `ID | severidad | resumen | disposición` (`aplicado`, `pregunta abierta`, `descartado` con motivo).
3. Preguntas abiertas que exigen decisión humana.
4. Resultado de la validación de OpenSpec.
5. Recordatorio: change **pendiente de revisión y aprobación humana**; no ejecutar apply.
