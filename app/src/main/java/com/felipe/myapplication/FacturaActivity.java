package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class FacturaActivity extends AppCompatActivity {

    private MaterialAutoCompleteTextView acPaciente;
    private TextView tvNumeroFactura;
    private TextView tvFacturaPaciente;
    private TextView tvFacturaHistoria;
    private TextView tvFacturaDni;
    private TextView tvFacturaFechaConsulta;
    private TextView tvFacturaMedico;
    private TextView tvFacturaValorConsulta;
    private TextView tvFacturaTotal;

    private MaterialButton btnCompartir;
    private MaterialButton btnEmitirFactura;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        inicializarVistas();
        configurarToolbar();
        configurarDropdownPaciente();
        configurarValoresDefault();
        configurarEventos();
        configurarBottomNav();
    }

    private void inicializarVistas() {
        acPaciente = findViewById(R.id.acPaciente);
        tvNumeroFactura = findViewById(R.id.tvNumeroFactura);
        tvFacturaPaciente = findViewById(R.id.tvFacturaPaciente);
        tvFacturaHistoria = findViewById(R.id.tvFacturaHistoria);
        tvFacturaDni = findViewById(R.id.tvFacturaDni);
        tvFacturaFechaConsulta = findViewById(R.id.tvFacturaFechaConsulta);
        tvFacturaMedico = findViewById(R.id.tvFacturaMedico);
        tvFacturaValorConsulta = findViewById(R.id.tvFacturaValorConsulta);
        tvFacturaTotal = findViewById(R.id.tvFacturaTotal);

        btnCompartir = findViewById(R.id.btnCompartir);
        btnEmitirFactura = findViewById(R.id.btnEmitirFactura);
        bottomNav = findViewById(R.id.bottomNav);
    }

    private void configurarToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void configurarDropdownPaciente() {
        List<String> nombresPacientes = new ArrayList<>();
        if (!PacientesActivity.LISTA_PACIENTES.isEmpty()) {
            for (Paciente p : PacientesActivity.LISTA_PACIENTES) {
                nombresPacientes.add(p.getNombre() + " " + p.getApellido() + " — " + p.getNumeroHistoria());
            }
        } else {
            nombresPacientes.add("Juan Pérez — HC-2024-001");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nombresPacientes);
        acPaciente.setAdapter(adapter);
        acPaciente.setText(nombresPacientes.get(0), false);
    }

    private void configurarValoresDefault() {
        Paciente p = !PacientesActivity.LISTA_PACIENTES.isEmpty() ? PacientesActivity.LISTA_PACIENTES.get(0) : new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");

        if (tvNumeroFactura != null) tvNumeroFactura.setText("N.º FACT-2026-001 · Clínica San Rafael");
        if (tvFacturaPaciente != null) tvFacturaPaciente.setText(p.getNombre() + " " + p.getApellido());
        if (tvFacturaHistoria != null) tvFacturaHistoria.setText(p.getNumeroHistoria());
        if (tvFacturaDni != null) tvFacturaDni.setText(p.getIdentidad() != null && !p.getIdentidad().isEmpty() ? p.getIdentidad() : "99887766D");
        if (tvFacturaFechaConsulta != null) tvFacturaFechaConsulta.setText("2025-09-10");
        if (tvFacturaMedico != null) tvFacturaMedico.setText("Dra. Laura Gómez");
        if (tvFacturaValorConsulta != null) tvFacturaValorConsulta.setText("$150.000 COP");
        if (tvFacturaTotal != null) tvFacturaTotal.setText("$150.000 COP");
    }

    private void configurarEventos() {
        if (btnCompartir != null) {
            btnCompartir.setOnClickListener(v ->
                Toast.makeText(this, "Factura lista para compartir", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnEmitirFactura != null) {
            btnEmitirFactura.setOnClickListener(v -> {
                Administrativo admin = new Administrativo("María", "López", "CC", "11223344C", 29, "Colombia", 1.65, "ADM-007", 1800000.0, "Recepción");
                Paciente p = !PacientesActivity.LISTA_PACIENTES.isEmpty() ? PacientesActivity.LISTA_PACIENTES.get(0) : new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");

                admin.generarFactura(p);
                Toast.makeText(this, "Factura emitida correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }

    private void configurarBottomNav() {
        if (bottomNav == null) return;
        bottomNav.setSelectedItemId(R.id.nav_agendar);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_inicio) {
                startActivity(new Intent(this, AdminInicioActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_agendar) {
                startActivity(new Intent(this, AgendarCitaActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_pacientes) {
                startActivity(new Intent(this, PacientesActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_personal) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }
}