package com.felipe.myapplication;

/** Proyección de una consulta persistida junto con el nombre de su paciente. */
public class ConsultaRegistro {
    public final long id;
    public final String fecha, motivo, medico, paciente;
    public ConsultaRegistro(long id, String fecha, String motivo, String medico, String paciente) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.medico = medico;
        this.paciente = paciente;
    }
}
