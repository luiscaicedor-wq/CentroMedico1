package com.felipe.myapplication;

public class Empleado extends Persona {
    private String id_empleado;
    private double salario;
    private String fechaIngreso;

    public Empleado(String nombre, String apellido, String tipoIdentidad, String identidad, int edad, String nacionalidad, double estatura, String id_empleado, double salario) {
        this(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura, id_empleado, salario, "");
    }

    public Empleado(String nombre, String apellido, String tipoIdentidad, String identidad, int edad, String nacionalidad, double estatura, String id_empleado, double salario, String fechaIngreso) {
        super(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura);
        this.id_empleado = id_empleado;
        this.salario = salario;
        this.fechaIngreso = fechaIngreso;
    }

    public String getId_empleado() { return id_empleado; }
    public double getSalario() { return salario; }
    public String getFechaIngreso() { return fechaIngreso; }
    public void setSalario(double salario) { this.salario = salario; }
    public void setFechaIngreso(String fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    @Override
    public String mostrarDatos() {
        return super.mostrarDatos() + "\n" + "ID empleado: " + id_empleado + "\n" + "Salario: $" + salario + "\n" + "Fecha de ingreso: " + fechaIngreso;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(mostrarDatos());
    }
}
