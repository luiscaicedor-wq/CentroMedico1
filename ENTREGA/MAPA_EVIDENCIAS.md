# Mapa de evidencias obligatorio — CentroMedico

> **Estado:** 16/16 filas documentadas. Las evidencias visuales y la demostración deben capturarse en Android Studio y el emulador; no se consideran verificadas por este documento.

| Código | Criterio | Archivo / evidencia localizada | Verificación pendiente |
|---|---|---|---|
| F1 | CRUD pacientes | PacientesActivity.java, RegistrarPaciente.java, PacienteDao.java | Crear, listar, detalle, editar, eliminar y reabrir; capturas pendientes |
| F2 | Validaciones | RegistrarPaciente.java, AgendarCitaActivity.java | Probar vacíos, edad fuera de rango y duplicados; capturas pendientes |
| F3 | Roles y sesión | Login.java, SessionManager.java | Mostrar roles y persistencia al reiniciar; no hay autenticación real |
| U1 | Layouts | app/src/main/res/layout/, values/strings.xml | Capturas de pantalla normal y pantalla alternativa pendientes |
| U2 | Navegación | AndroidManifest.xml, AgendarCitaActivity.java | Mostrar navegación, estados vacíos y error; capturas pendientes |
| M1 | Manifest | app/src/main/AndroidManifest.xml | Captura o inspección de launcher, actividades y permisos |
| P1 | Esquema SQLite | DatabaseHelper.java | Tablas pacientes/consultas, PK y FK; captura Database Inspector pendiente |
| P2 | DAO | PacienteDao.java, ConsultaDao.java, *Repository.java | Mostrar DAO y ejecución en executor; verificar con prueba |
| P3 | Capas | PacienteViewModel.java, ConsultaViewModel.java, *Repository.java | Demostrar UI → VM → Repository → DAO en CRUD y citas |
| O1 | POO | Paciente.java, Consulta.java, Empleado.java, CentroMedico.java | Explicar responsabilidades y encapsulamiento |
| O2 | Polimorfismo | Persona.java, Empleado.java, Medico.java, Enfermero.java, IReportable.java | Mostrar interfaz, herencia y sobrescritura reales |
| O3 | UML | ENTREGA/UML_CLASES.md, ENTREGA/UML_SECUENCIA.md | Exportar diagramas y contrastar con código |
| D1 | Planificación | ENTREGA/PLANIFICACION.md | Completar fechas reales, cronograma y riesgos |
| D2 | Calidad | app/src/main/java/; ENTREGA/REVISION.md | Adjuntar captura de compilación y resultados de pruebas |
| E1 | Demo | ENTREGA/GUION_DEMO.md | Grabar o presentar demo 3–5 min y adjuntar evidencia |
| E2 | Aporte individual | ENTREGA/PLANIFICACION.md; historial Git | Usuario desarrolla solo; aportar commits y explicación de contribución |
