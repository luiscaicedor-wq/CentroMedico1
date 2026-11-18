package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistorialMedicoActivity extends AppCompatActivity {

    private MaterialToolbar toolbarHistorial;
    private TextView tvHistorialDiagnostico;
    private TextView tvHistorialTratamiento;
    private BottomNavigationView bottomNavHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_medico_app);

        inicializarVistas();
        configurarToolbar();
        mostrarDatosHistorial();
        configurarBottomNav();
    }

    private void inicializarVistas() {
        toolbarHistorial = findViewById(R.id.toolbarHistorial);
        tvHistorialDiagnostico = findViewById(R.id.tvHistorialDiagnostico);
        tvHistorialTratamiento = findViewById(R.id.tvHistorialTratamiento);
        bottomNavHistorial = findViewById(R.id.bottomNavHistorial);
    }

    private void configurarToolbar() {
        if (toolbarHistorial != null) {
            toolbarHistorial.setNavigationOnClickListener(v -> finish());
        }
    }

    private void mostrarDatosHistorial() {
        Paciente paciente = !PacientesActivity.LISTA_PACIENTES.isEmpty() ? PacientesActivity.LISTA_PACIENTES.get(0) : new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");
        paciente.mostrarHistorial();

        if (tvHistorialDiagnostico != null) {
            tvHistorialDiagnostico.setText(RealizarConsultaActivity.ULTIMO_DIAGNOSTICO);
        }
        if (tvHistorialTratamiento != null) {
            tvHistorialTratamiento.setText(RealizarConsultaActivity.ULTIMO_TRATAMIENTO);
        }
    }

    private void configurarBottomNav() {
        if (bottomNavHistorial == null) return;
        bottomNavHistorial.setSelectedItemId(R.id.nav_historial);
        bottomNavHistorial.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_agenda) {
                startActivity(new Intent(this, MedicoInicioActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_consulta) {
                startActivity(new Intent(this, RealizarConsultaActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_historial) {
                return true;
            }
            return false;
        });
    }
}