# Planificación — CentroMedico

## Objetivo
Desarrollar una aplicación Android en Java para gestionar pacientes y citas de un centro médico con persistencia local SQLite y acceso diferenciado por roles.

## Alcance
CRUD de pacientes, agendamiento/listado/eliminación de citas, roles administrativo/médico/enfermería, pantallas de historial, consultas y facturación según implementación actual. **No se exige API externa.** La autenticación actual es demostrativa: no valida usuarios contra servidor.

## Responsable y roles
Proyecto desarrollado individualmente. Una sola persona realiza análisis, diseño, desarrollo, pruebas y presentación. Adjuntar historial de commits como evidencia de autoría.

## Cronograma de referencia (completar con fechas reales)
| Fase | Actividad | Evidencia |
|---|---|---|
| 1 | Requisitos y diseño de clases | UML, rúbrica |
| 2 | Implementación de pantallas y dominio | XML y Java |
| 3 | Persistencia y CRUD | SQLite, DAO, Repository, ViewModel |
| 4 | Pruebas funcionales y correcciones | Capturas de ejecución |
| 5 | Documentación y presentación | Mapa de evidencias y demo |

## Riesgos y mitigación
- Cierre inesperado por SQLite: DAO ejecutado en executor, captura de errores y mensajes de UI.
- Duplicados de DNI o historia: índices UNIQUE; mostrar fallo de inserción.
- Pérdida de datos al actualizar la versión: **no aumentar DATABASE_VERSION sin migración**; `onUpgrade()` original borra tablas.
- Datos temporales y módulos simulados: distinguir persistencia real de demostraciones.
- Cambios de tamaño de pantalla: verificar en otro dispositivo virtual.

## Pendiente de completar
Fechas reales del cronograma, enlace del repositorio y capturas firmadas de la demostración.
