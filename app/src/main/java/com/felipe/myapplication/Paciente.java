package com.felipe.myapplication;

public class Paciente extends Persona {

    private long id;
    private String numeroHistoria;
    private HistorialMedico historial;

    public Paciente() {
        super();
        this.historial = new HistorialMedico();
    }

    public Paciente(String nombre, String apellido, String tipoIdentidad, String identidad, int edad, String nacionalidad, double estatura) {
        super(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura);
        this.historial = new HistorialMedico();
    }

    public Paciente(String nombre, String apellido, String tipoIdentidad, int edad, String numeroHistoria) {
        this(nombre, apellido, tipoIdentidad, "", edad, "Colombia", 1.70);
        this.numeroHistoria = numeroHistoria;
    }

    // Constructor usado por el formulario de registro de pacientes.
    public Paciente(String nombre, String apellido, String tipoIdentidad, String identidad,
                    int edad, String nacionalidad, double estatura, String numeroHistoria) {
        this(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura);
        this.numeroHistoria = numeroHistoria;
    }

    // Constructor completo con ID
    public Paciente(long id, String nombre, String apellido, String tipoIdentidad, String identidad,
                    int edad, String nacionalidad, double estatura, String numeroHistoria) {
        this(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura, numeroHistoria);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeroHistoria() {
        return numeroHistoria;
    }

    public void setNumeroHistoria(String numeroHistoria) {
        this.numeroHistoria = numeroHistoria;
    }

    public String getDni() {
        return getIdentidad();
    }

    public void setDni(String dni) {
        setIdentidad(dni);
    }

    public HistorialMedico getHistorial() {
        return historial;
    }

    public void setHistorial(HistorialMedico historial) {
        this.historial = historial;
    }

    public void agregarConsulta(Consulta cn) {
        System.out.println("Su consulta ha sido generada con éxito.");
    }

    public void mostrarHistorial() {
        if (historial != null) {
            historial.mostrar();
        } else {
            System.out.println("El paciente no tiene historial médico.");
        }
    }

    @Override
    public String generarReporteResumido() {
        return "PACIENTE: " + getNombre() + " " + getApellido() + " | HC: " + numeroHistoria + " | DNI: " + getIdentidad();
    }

    @Override
    public String obtenerIdentificadorUnico() {
        return numeroHistoria != null ? numeroHistoria : getIdentidad();
    }
}
