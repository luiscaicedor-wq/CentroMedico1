
package com.felipe.myapplication;

public class Consulta {

    private String fecha;
    private String motivo;
    private Medico medico;
    private Paciente paciente;

    public Consulta(String fecha, String motivo, Medico m, Paciente p) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.medico = m;
        this.paciente = p;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void realizar() {
        System.out.println(
                "Consulta realizada el " + fecha +
                        " por el médico " + medico.getNombre() +
                        " para el paciente " + paciente.getNombre() +
                        ". Motivo: " + motivo
        );
    }
}
