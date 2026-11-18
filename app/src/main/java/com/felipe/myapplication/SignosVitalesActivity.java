package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class SignosVitalesActivity extends AppCompatActivity {

    private MaterialToolbar toolbarSignos;
    private MaterialButton btnGuardarSignos;
    private BottomNavigationView bottomNavSignos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signos_vitales);

        inicializarVistas();
        configurarToolbar();
        configurarEventos();
        configurarBottomNav();
    }

    private void inicializarVistas() {
        toolbarSignos = findViewById(R.id.toolbarSignos);
        btnGuardarSignos = findViewById(R.id.btnGuardarSignos);
        bottomNavSignos = findViewById(R.id.bottomNavSignos);
    }

    private void configurarToolbar() {
        if (toolbarSignos != null) {
            toolbarSignos.setNavigationOnClickListener(v -> finish());
        }
    }

    private void configurarEventos() {
        if (btnGuardarSignos != null) {
            btnGuardarSignos.setOnClickListener(v -> guardarSignos());
        }
    }

    private void guardarSignos() {
        Enfermero enfermero = new Enfermero("Felipe", "Romo", "CC", "87654321B", 35, "Colombia", 1.75, "EMP-014", 2200000.0, "Mañana");
        Paciente paciente = !PacientesActivity.LISTA_PACIENTES.isEmpty() ? PacientesActivity.LISTA_PACIENTES.get(0) : new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");

        enfermero.tomarSignosVitales(paciente);
        EnfermeroInicioActivity.REGISTROS_HECHOS++;

        Toast.makeText(this, "Signos vitales registrados correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void configurarBottomNav() {
        if (bottomNavSignos == null) return;
        bottomNavSignos.setSelectedItemId(R.id.nav_signos);
        bottomNavSignos.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_turno) {
                startActivity(new Intent(this, EnfermeroInicioActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_signos) {
                return true;
            } else if (itemId == R.id.nav_medicacion) {
                startActivity(new Intent(this, AdministrarMedicamentoActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }
}