package com.felipe.myapplication;

import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {

    private List<String> diagnosticos = new ArrayList<>();
    private List<String> tratamientos = new ArrayList<>();

    public void agregarDiagnostico(String diagnostico) {
        diagnosticos.add(diagnostico);
    }

    public void agregarTratamiento(String tratamiento) {
        tratamientos.add(tratamiento);
    }

    public void mostrar() {
        System.out.println("Diagnosticos: " + diagnosticos);
        System.out.println("Tratamientos: " + tratamientos);
    }
}