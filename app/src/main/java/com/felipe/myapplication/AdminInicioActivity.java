package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminInicioActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private PacienteViewModel pacienteViewModel;
    private ConsultaViewModel consultaViewModel;
    private View cardRegistrarPaciente;
    private View cardAgendarCita;
    private View cardVerPacientes;
    private View cardGenerarFactura;

    private TextView tvTotalPacientes;
    private TextView tvTotalConsultas;
    private TextView tvTotalEmpleados;

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inicio);

        inicializarVistas();
        pacienteViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);
        consultaViewModel = new ViewModelProvider(this).get(ConsultaViewModel.class);
        pacienteViewModel.getPacientes().observe(this, pacientes -> {
            if (tvTotalPacientes != null && pacientes != null)
                tvTotalPacientes.setText(String.valueOf(pacientes.size()));
        });
        consultaViewModel.getTotal().observe(this, total -> {
            if (tvTotalConsultas != null && total != null)
                tvTotalConsultas.setText(total < 0 ? "—" : String.valueOf(total));
        });
        configurarEventos();
        configurarBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarMetricas();
    }

    private void inicializarVistas() {
        toolbar = findViewById(R.id.toolbar);
        cardRegistrarPaciente = findViewById(R.id.cardRegistrarPaciente);
        cardAgendarCita = findViewById(R.id.cardAgendarCita);
        cardVerPacientes = findViewById(R.id.cardVerPacientes);
        cardGenerarFactura = findViewById(R.id.cardGenerarFactura);

        tvTotalPacientes = findViewById(R.id.tvTotalPacientes);
        tvTotalConsultas = findViewById(R.id.tvTotalConsultas);
        tvTotalEmpleados = findViewById(R.id.tvTotalEmpleados);

        bottomNav = findViewById(R.id.bottomNav);
    }

    private void configurarEventos() {
        if (toolbar != null) {
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_cerrar_sesion) {
                    new SessionManager(this).cerrarSesion();
                    Intent intent = new Intent(AdminInicioActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            });
        }

        if (cardRegistrarPaciente != null) {
            cardRegistrarPaciente.setOnClickListener(v -> {
                Intent intent = new Intent(AdminInicioActivity.this, RegistrarPaciente.class);
                startActivity(intent);
            });
        }

        if (cardAgendarCita != null) {
            cardAgendarCita.setOnClickListener(v -> {
                Intent intent = new Intent(AdminInicioActivity.this, AgendarCitaActivity.class);
                startActivity(intent);
            });
        }

        if (cardVerPacientes != null) {
            cardVerPacientes.setOnClickListener(v -> {
                Intent intent = new Intent(AdminInicioActivity.this, PacientesActivity.class);
                startActivity(intent);
            });
        }

        if (cardGenerarFactura != null) {
            cardGenerarFactura.setOnClickListener(v -> {
                Intent intent = new Intent(AdminInicioActivity.this, FacturaActivity.class);
                startActivity(intent);
            });
        }
    }

    private void configurarBottomNav() {
        if (bottomNav == null) return;
        bottomNav.setSelectedItemId(R.id.nav_inicio);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_inicio) {
                return true;
            } else if (itemId == R.id.nav_pacientes) {
                startActivity(new Intent(AdminInicioActivity.this, PacientesActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_agendar) {
                startActivity(new Intent(AdminInicioActivity.this, AgendarCitaActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_personal) {
                startActivity(new Intent(AdminInicioActivity.this, MainActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void actualizarMetricas() {
        pacienteViewModel.cargarPacientes();
        consultaViewModel.cargar();
        if (tvTotalEmpleados != null) tvTotalEmpleados.setText("3");
    }
}
