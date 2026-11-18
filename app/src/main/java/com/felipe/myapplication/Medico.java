package com.felipe.myapplication;

public class Medico extends Empleado {
    private String especialidad;
    private String numeroColegiado;

    public Medico(String nombre, String apellido, String tipoIdentidad, int edad, String id_empleado, double salario, String fechaIngreso, String especialidad, String numeroColegiado) {
        super(nombre, apellido, tipoIdentidad, "", edad, "Colombia", 1.70, id_empleado, salario, fechaIngreso);
        this.especialidad = especialidad;
        this.numeroColegiado = numeroColegiado;
    }

    public Medico(String nombre, String apellido, String tipoIdentidad, String identidad, int edad, String nacionalidad, double estatura, String id_empleado, double salario, String especialidad, String numeroColegiado) {
        super(nombre, apellido, tipoIdentidad, identidad, edad, nacionalidad, estatura, id_empleado, salario);
        this.especialidad = especialidad;
        this.numeroColegiado = numeroColegiado;
    }

    public String getEspecialidad() { return especialidad; }
    public String getNumeroColegiado() { return numeroColegiado; }

    @Override
    public String mostrarDatos() {
        return super.mostrarDatos() + "\n" + "Especialidad: " + especialidad + "\n" + "Número colegiado: " + numeroColegiado;
    }

    public String diagnosticar(Paciente p, String diagnostico) {
        return "El paciente " + p.getNombre() + " tiene diagnóstico: " + diagnostico;
    }

    public String prescribir(Paciente obj) {
        return "El paciente " + obj.getNombre() +
                " debe tomar acetaminofén cada 12 horas.";
    }
}
