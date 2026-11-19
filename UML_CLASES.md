# Diagrama UML de clases (núcleo persistente)

```mermaid
classDiagram
    class Persona
    class Empleado
    class Medico
    class Enfermero
    class Paciente
    class Consulta
    class ConsultaRegistro
    class DatabaseHelper
    class PacienteDao
    class PacienteRepository
    class PacienteViewModel
    class ConsultaDao
    class ConsultaRepository
    class ConsultaViewModel
    class IReportable
    Persona <|-- Paciente
    Persona <|-- Empleado
    Empleado <|-- Medico
    Empleado <|-- Enfermero
    Consulta --> Paciente
    Consulta --> Medico
    PacienteViewModel --> PacienteRepository
    PacienteRepository --> PacienteDao
    PacienteDao --> DatabaseHelper
    ConsultaViewModel --> ConsultaRepository
    ConsultaRepository --> ConsultaDao
    ConsultaDao --> DatabaseHelper
    ConsultaDao ..> ConsultaRegistro
```

Las clases `ConsultaRegistro` representan la proyección persistida de las citas; `Consulta` es la entidad original de dominio. Revisar la interfaz `IReportable` y su implementación para explicarla en la exposición.
