# Revisión técnica de MyApplicationFinal12

## Cambios realizados sobre copia del ZIP
- Se agregaron `ConsultaDao`, `ConsultaRepository`, `ConsultaViewModel` y `ConsultaRegistro`.
- Agendar cita carga pacientes por `PacienteViewModel` y guarda citas por `ConsultaViewModel`, sin SQL en la Activity ni acceso a base de datos en el hilo principal.
- Se agregó la lista de citas persistidas debajo del formulario, con estado vacío y eliminación confirmada.
- Se eliminó la navegación automática tras guardar; ahora permanece visible la lista para comprobar el resultado.
- El contador de pacientes y consultas del panel se obtiene por ViewModel desde SQLite.
- Se retiró el paciente ficticio de la lista estática de la pantalla Pacientes; el registro inicial de SQLite permanece.
- Se añadieron mensajes ante fallos de registro y actualización de pacientes y manejo de excepciones de SQLite en operaciones asíncronas.
- Se añadieron cadenas de texto para la nueva sección de citas.

## Límites importantes
- **Compilación APK y ejecución en emulador no verificadas en este entorno**: no hay SDK Android/Gradle instalado. Abrir en Android Studio y ejecutar `Build > Make Project`.
- La interfaz de algunas pantallas conserva textos literales; para U1 superior aún conviene mover todos los textos a `strings.xml`.
- El login utiliza roles y SharedPreferences, pero no verifica credenciales reales.
- El módulo de personal, la realización de consultas y la facturación contienen elementos de demostración o datos en memoria que deben revisarse antes de afirmar persistencia total.
- `DatabaseHelper.onUpgrade()` original elimina datos; no modificar la versión sin migración.
- El mapa de evidencias incluye las 16 filas, pero necesita capturas reales, prueba de funcionamiento y enlace al repositorio.
- La rúbrica pide un DAO por entidad; los módulos secundarios aún no cuentan todos con DAO/Repository/ViewModel.

## Pruebas manuales prioritarias
1. Instalar y abrir; cambiar de rol y volver a abrir para comprobar sesión.
2. Crear paciente; editar; buscar; ver detalle; eliminar; reiniciar app.
3. Agendar cita; confirmar mensaje; ver lista; reiniciar; confirmar persistencia; eliminar.
4. Comprobar que el contador de consultas coincide con SQLite.
5. Intentar fecha/motivo vacíos, DNI duplicado y edad fuera de rango.
6. Rotar dispositivo y repetir en otro tamaño de pantalla.
7. Probar cada botón de navegación, historial, facturación y personal.
