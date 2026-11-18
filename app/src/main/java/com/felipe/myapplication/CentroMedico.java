package com.felipe.myapplication;

import java.util.ArrayList;
import java.util.List;

public class CentroMedico {
    private String nombre;
    private List<Empleado> personal = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();

    public CentroMedico(String nombre) {
        this.nombre = nombre;
    }

    public void contratar(Empleado empleado) {
        personal.add(empleado);
    }

    public void registrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void agendarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public void mostrarPersonal() {
        System.out.println("Personal del centro: " + nombre);
        for (Empleado empleado : personal) {
            empleado.mostrarInformacion();
        }
    }

    public void mostrarPacientes() {
        System.out.println("Pacientes registrados: ");
        for (Paciente paciente : pacientes) {
            paciente.mostrarInformacion();
        }
    }

    public String getNombre() {
        return nombre;
    }
}