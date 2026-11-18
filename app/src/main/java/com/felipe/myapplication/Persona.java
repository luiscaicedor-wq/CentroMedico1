package com.felipe.myapplication;

public class Persona implements IReportable {
    private String nombre;
    private String apellido;
    private String tipoIdentidad;
    private String identidad;
    private int edad;
    private String nacionalidad;
    private double estatura;

    public Persona() {
    }

    public Persona(String nombre, String apellido, String tipoIdentidad, String identidad, int edad, String nacionalidad, double estatura) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoIdentidad = tipoIdentidad;
        this.identidad = identidad;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.estatura = estatura;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTipoIdentidad() { return tipoIdentidad; }
    public String getIdentidad() { return identidad; }
    public int getEdad() { return edad; }
    public String getNacionalidad() { return nacionalidad; }
    public double getEstatura() { return estatura; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellido) { this.apellido = apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setTipoIdentidad(String tipoIdentidad) { this.tipoIdentidad = tipoIdentidad; }
    public void setIdentidad(String identidad) { this.identidad = identidad; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public void setEstatura(double estatura) { this.estatura = estatura; }

    public String mostrarDatos() {
        return "Nombre: " + nombre + "\n" + "Apellido: " + apellido + "\n" + "Tipo de identidad: " + tipoIdentidad + "\n" + "Identidad: " + identidad + "\n" + "Edad: " + edad + " años\n" + "Nacionalidad: " + nacionalidad + "\n" + "Estatura: " + estatura + " m";
    }

    public void mostrarInformacion() {
        System.out.println(mostrarDatos());
    }

    @Override
    public String generarReporteResumido() {
        return nombre + " " + apellido + " (" + identidad + ")";
    }

    @Override
    public String obtenerIdentificadorUnico() {
        return identidad != null ? identidad : "N/A";
    }
}
