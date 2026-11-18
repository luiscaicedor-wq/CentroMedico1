package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

public class AdministrarMedicamentoActivity extends AppCompatActivity {

    private MaterialToolbar toolbarMedicar;
    private TextInputEditText etNombreMedicamento;
    private TextInputEditText etDosisMedicamento;
    private MaterialAutoCompleteTextView acViaAdministracion;
    private MaterialButton btnRegistrarAdministracion;
    private BottomNavigationView bottomNavMedicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_medicamento);

        inicializarVistas();
        configurarToolbar();
        configurarDropdownVia();
        configurarEventos();
        configurarBottomNav();
    }

    private void inicializarVistas() {
        toolbarMedicar = findViewById(R.id.toolbarMedicar);
        etNombreMedicamento = findViewById(R.id.etNombreMedicamento);
        etDosisMedicamento = findViewById(R.id.etDosisMedicamento);
        acViaAdministracion = findViewById(R.id.acViaAdministracion);
        btnRegistrarAdministracion = findViewById(R.id.btnRegistrarAdministracion);
        bottomNavMedicar = findViewById(R.id.bottomNavMedicar);
    }

    private void configurarToolbar() {
        if (toolbarMedicar != null) {
            toolbarMedicar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void configurarDropdownVia() {
        if (acViaAdministracion != null) {
            String[] vias = new String[]{"Oral", "Intravenosa", "Intramuscular", "Subcutánea"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, vias);
            acViaAdministracion.setAdapter(adapter);
            acViaAdministracion.setText(vias[0], false);
        }
    }

    private void configurarEventos() {
        if (btnRegistrarAdministracion != null) {
            btnRegistrarAdministracion.setOnClickListener(v -> registrarAdministracion());
        }
    }

    private void registrarAdministracion() {
        String medicamento = etNombreMedicamento != null && etNombreMedicamento.getText() != null ? etNombreMedicamento.getText().toString() : "Acetaminofén";
        String dosis = etDosisMedicamento != null && etDosisMedicamento.getText() != null ? etDosisMedicamento.getText().toString() : "500";
        String via = acViaAdministracion != null ? acViaAdministracion.getText().toString() : "Oral";

        if (TextUtils.isEmpty(medicamento)) medicamento = "Acetaminofén";
        if (TextUtils.isEmpty(dosis)) dosis = "500";

        Enfermero enfermero = new Enfermero("Felipe", "Romo", "CC", "87654321B", 35, "Colombia", 1.75, "EMP-014", 2200000.0, "Mañana");
        Paciente paciente = !PacientesActivity.LISTA_PACIENTES.isEmpty() ? PacientesActivity.LISTA_PACIENTES.get(0) : new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");

        String res = enfermero.administrarMedicamento(paciente);
        EnfermeroInicioActivity.REGISTROS_HECHOS++;

        Toast.makeText(this, res + " (" + dosis + "mg " + via + ")", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void configurarBottomNav() {
        if (bottomNavMedicar == null) return;
        bottomNavMedicar.setSelectedItemId(R.id.nav_medicacion);
        bottomNavMedicar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_turno) {
                startActivity(new Intent(this, EnfermeroInicioActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_signos) {
                startActivity(new Intent(this, SignosVitalesActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_medicacion) {
                return true;
            }
            return false;
        });
    }
}