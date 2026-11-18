package com.felipe.myapplication;

public class Enfermero extends Empleado {

    private String turno;

    public Enfermero(String nombre, String apellido, String tipoIdentidad, int edad,String id_empleado, double salario, String fechaIngreso, String turno) {
        super(nombre, apellido, tipoIdentidad, "", edad, "Colombia", 1.70, id_empleado, salario, fechaIngreso);
        this.turno = turno;
    }

    public Enfermero(String nombre, String apellido, String tipoIdentidad, String identidad,int edad, String nacionalidad, double estatura,String id_empleado, double salario, String turno) {
        super(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura, id_empleado, salario);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public void tomarSignosVitales(Paciente p) {
        System.out.println("Signos vitales dentro de los parámetros normales para " + p.getNombre());
    }

    public String administrarMedicamento(Paciente p) {
        return "El paciente " + p.getNombre() + " recibió acetaminofén.";
    }
}