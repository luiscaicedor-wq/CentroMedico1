package com.felipe.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Principal{

    public static void main(String[] args) {

        CentroMedico centro = new CentroMedico("Clínica San Rafael");

        // Crear empleados
        Medico medico1 = new Medico("Laura", "Gómez", "12345678A", 42, "MED-001", 4500.0, "2018-03-15", "Cardiología", "COL-98765");

        Enfermero enfermero1 = new Enfermero("Carlos", "Ruiz", "87654321B", 35, "ENF-014", 2200.0, "2020-06-01", "Mañana");

        Administrativo admin1 = new Administrativo("María", "López", "11223344C", 29, "ADM-007", 1800.0, "2021-01-10", "Recepción");

        // Contratar
        centro.contratar(medico1);
        centro.contratar(enfermero1);
        centro.contratar(admin1);

        // Registrar paciente
        Paciente paciente1 = new Paciente("Juan", "Pérez", "99887766D", 55, "HC-2024-001");
        centro.registrarPaciente(paciente1);

        Paciente paciente2 = new Paciente("Carlos", "Belalcazar", "0066", 30, "HC-2026-001");
        centro.registrarPaciente(paciente2);


        // Mostrar información
        centro.mostrarPersonal();
        centro.mostrarPacientes();

        // Agendar y realizar consulta
        admin1.agendarCita(paciente1, medico1, "2025-09-10");
        Consulta consulta1 = new Consulta("2025-09-10", "Dolor en el pecho", medico1, paciente1);
        Consulta consulta2 = new Consulta("2025-09-11", "Cefalea", medico1, paciente2);
        centro.agendarConsulta(consulta1);
        consulta1.realizar();
        centro.agendarConsulta(consulta2);
        consulta2.realizar();
        medico1.diagnosticar(paciente2, "Migraña");

        // Ver historial del paciente
        paciente1.mostrarHistorial();
        paciente2.mostrarHistorial();
    }
}

