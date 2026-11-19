# CentroMedico — verificación de entrega

## Comprobaciones realizadas

- Revisión de fuentes Java, manifiesto, esquema SQLite, clases DAO/Repository/ViewModel y estructura de la entrega.
- Corrección de las lambdas en `PacienteRepository` (versión anterior).
- Mensajes visibles para documentos o historias clínicas duplicados y resultados fallidos de eliminación.
- Manejo de excepciones al consultar pacientes y validación de fechas pasadas en citas.
- El usuario confirmó en emulador: apertura de la app, CRUD de pacientes, conservación de citas y eliminación persistente.
- ZIP comprobado íntegro.

## Límites de la verificación

No se ha ejecutado una compilación Android ni una prueba instrumental de todas las pantallas en este entorno. No es posible garantizar ausencia de cierres inesperados. Se debe ejecutar **Build > Make Project** y probar los flujos del guion antes de la entrega.

La selección de roles en `Login` persiste en preferencias, pero no equivale a autenticación segura. Los módulos secundarios de la app conservan algunas funciones de demostración; no se debe presentarlos como persistentes si no se verificaron.

## Entrega académica

El mapa de evidencias de 16 filas está en `MAPA_EVIDENCIAS.md`, pero las capturas y la demostración deben ser reales. Completar las fechas y datos reales de planificación y mostrar el historial Git como evidencia de autoría individual.
