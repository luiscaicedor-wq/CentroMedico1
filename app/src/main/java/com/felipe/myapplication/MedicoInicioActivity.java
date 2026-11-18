package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class MedicoInicioActivity extends AppCompatActivity {

    private MaterialToolbar toolbarMedico;
    private MaterialButton btnIniciarConsulta;
    private BottomNavigationView bottomNavMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_inicio);

        inicializarVistas();
        configurarToolbar();
        configurarEventos();
        configurarBottomNav();
    }

    private void inicializarVistas() {
        toolbarMedico = findViewById(R.id.toolbarMedico);
        btnIniciarConsulta = findViewById(R.id.btnIniciarConsulta);
        bottomNavMedico = findViewById(R.id.bottomNavMedico);
    }

    private void configurarToolbar() {
        if (toolbarMedico != null) {
            toolbarMedico.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_cerrar_sesion) {
                    new SessionManager(this).cerrarSesion();
                    Intent intent = new Intent(MedicoInicioActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            });
        }
    }

    private void configurarEventos() {
        if (btnIniciarConsulta != null) {
            btnIniciarConsulta.setOnClickListener(v -> {
                Intent intent = new Intent(MedicoInicioActivity.this, RealizarConsultaActivity.class);
                startActivity(intent);
            });
        }
    }

    private void configurarBottomNav() {
        if (bottomNavMedico == null) return;
        bottomNavMedico.setSelectedItemId(R.id.nav_agenda);
        bottomNavMedico.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_agenda) {
                return true;
            } else if (itemId == R.id.nav_consulta) {
                startActivity(new Intent(MedicoInicioActivity.this, RealizarConsultaActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_historial) {
                startActivity(new Intent(MedicoInicioActivity.this, HistorialMedicoActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }
}