package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RealizarConsultaActivity extends AppCompatActivity {

    public static String ULTIMO_DIAGNOSTICO = "Angina de pecho leve";
    public static String ULTIMO_TRATAMIENTO = "Acetaminofén 500mg c/12h";

    private MaterialToolbar toolbarConsulta;
    private View btnVerHistorialIcono;
    private TextInputEditText etDiagnosticoConsulta;
    private TextInputEditText etTratamientoConsulta;
    private TextInputEditText etIndicacionesConsulta;

    private MaterialButton btnGuardarConsulta;
    private MaterialButton btnFinalizarConsulta;
    private BottomNavigationView bottomNavConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_consulta);

        inicializarVistas();
        configurarToolbar();
        configurarEventos();
        configurarBottomNav();
    }

    private void inicializarVistas() {
        toolbarConsulta = findViewById(R.id.toolbarConsulta);
        btnVerHistorialIcono = findViewById(R.id.btnVerHistorialIcono);
        etDiagnosticoConsulta = findViewById(R.id.etDiagnosticoConsulta);
        etTratamientoConsulta = findViewById(R.id.etTratamientoConsulta);
        etIndicacionesConsulta = findViewById(R.id.etIndicacionesConsulta);

        btnGuardarConsulta = findViewById(R.id.btnGuardarConsulta);
        btnFinalizarConsulta = findViewById(R.id.btnFinalizarConsulta);
        bottomNavConsulta = findViewById(R.id.bottomNavConsulta);
    }

    private void configurarToolbar() {
        if (toolbarConsulta != null) {
            toolbarConsulta.setNavigationOnClickListener(v -> finish());
        }
    }

    private void configurarEventos() {
        if (btnVerHistorialIcono != null) {
            btnVerHistorialIcono.setOnClickListener(v -> {
                Intent intent = new Intent(RealizarConsultaActivity.this, HistorialMedicoActivity.class);
                startActivity(intent);
            });
        }

        if (btnGuardarConsulta != null) {
            btnGuardarConsulta.setOnClickListener(v -> {
                Toast.makeText(this, "Borrador de consulta guardado", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnFinalizarConsulta != null) {
            btnFinalizarConsulta.setOnClickListener(v -> finalizarConsulta());
        }
    }

    private void finalizarConsulta() {
        String diagnostico = obtenerTexto(etDiagnosticoConsulta);
        String tratamiento = obtenerTexto(etTratamientoConsulta);

        if (TextUtils.isEmpty(diagnostico)) diagnostico = "Angina de pecho leve";
        if (TextUtils.isEmpty(tratamiento)) tratamiento = "Acetaminofén 500mg c/12h";

        ULTIMO_DIAGNOSTICO = diagnostico;
        ULTIMO_TRATAMIENTO = tratamiento;

        Medico medico = new Medico("Camila", "Cifuentes", "CC", "12345678A", 42, "Colombia", 1.70, "MED-001", 4500000.0, "Cardiología", "COL-98765");
        Paciente paciente = !PacientesActivity.LISTA_PACIENTES.isEmpty() ? PacientesActivity.LISTA_PACIENTES.get(0) : new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");

        Consulta consulta = new Consulta("2025-09-10", "Dolor en el pecho", medico, paciente);
        consulta.realizar();

        medico.diagnosticar(paciente, diagnostico);
        medico.prescribir(paciente);

        Toast.makeText(this, "Consulta realizada y diagnóstico registrado", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RealizarConsultaActivity.this, HistorialMedicoActivity.class);
        startActivity(intent);
        finish();
    }

    private String obtenerTexto(TextInputEditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }

    private void configurarBottomNav() {
        if (bottomNavConsulta == null) return;
        bottomNavConsulta.setSelectedItemId(R.id.nav_consulta);
        bottomNavConsulta.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_agenda) {
                startActivity(new Intent(this, MedicoInicioActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_consulta) {
                return true;
            } else if (itemId == R.id.nav_historial) {
                startActivity(new Intent(this, HistorialMedicoActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }
}