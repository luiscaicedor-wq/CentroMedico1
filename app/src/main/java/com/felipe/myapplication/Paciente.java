package com.felipe.myapplication;

public class Paciente extends Persona {

    private String numeroHistoria;
    private HistorialMedico historial;

    public Paciente(String nombre, String apellido, String tipoIdentidad, String identidad,int edad, String nacionalidad, double estatura) {
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

    public String getNumeroHistoria() {
        return numeroHistoria;
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
}