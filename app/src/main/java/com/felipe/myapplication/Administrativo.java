package com.felipe.myapplication;

public class Administrativo extends Empleado {
    private String area;

    public Administrativo(String nombre, String apellido, String tipoIdentidad, int edad,String id_empleado, double salario, String fechaIngreso, String area) {
        super(nombre, apellido, tipoIdentidad, "", edad, "Colombia", 1.70, id_empleado, salario, fechaIngreso);
        this.area = area;
    }

    public Administrativo(String nombre, String apellido, String tipoIdentidad, String identidad, int edad, String nacionalidad, double estatura, String id_empleado, double salario, String area) {
        super(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura, id_empleado, salario);
        this.area = area;
    }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    @Override
    public String mostrarDatos() {
        return super.mostrarDatos() + "\n" + "Área: " + area;
    }

    public void agendarCita(Paciente p, Medico m, String fecha) {
        System.out.println("Cita agendada para " + p.getNombre() + " con " + m.getNombre() + ". Fecha: " + fecha);
    }

    public void generarFactura(Paciente p) {
        System.out.println("Factura generada para " + p.getNombre());
    }
}
