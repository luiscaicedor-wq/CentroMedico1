package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class EnfermeroInicioActivity extends AppCompatActivity {

    public static int REGISTROS_HECHOS = 0;

    private MaterialToolbar toolbarEnfermero;
    private TextView tvPacientesAsignados;
    private TextView tvRegistrosHechos;

    private MaterialButton btnTomarSignos;
    private MaterialButton btnMedicar;
    private BottomNavigationView bottomNavEnfermero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfermero_inicio);

        inicializarVistas();
        configurarToolbar();
        configurarEventos();
        configurarBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarMetricas();
    }

    private void inicializarVistas() {
        toolbarEnfermero = findViewById(R.id.toolbarEnfermero);
        tvPacientesAsignados = findViewById(R.id.tvPacientesAsignados);
        tvRegistrosHechos = findViewById(R.id.tvRegistrosHechos);

        btnTomarSignos = findViewById(R.id.btnTomarSignos);
        btnMedicar = findViewById(R.id.btnMedicar);
        bottomNavEnfermero = findViewById(R.id.bottomNavEnfermero);
    }

    private void configurarToolbar() {
        if (toolbarEnfermero != null) {
            toolbarEnfermero.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_cerrar_sesion) {
                    Intent intent = new Intent(EnfermeroInicioActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            });
        }
    }

    private void configurarEventos() {
        if (btnTomarSignos != null) {
            btnTomarSignos.setOnClickListener(v -> {
                Intent intent = new Intent(EnfermeroInicioActivity.this, SignosVitalesActivity.class);
                startActivity(intent);
            });
        }

        if (btnMedicar != null) {
            btnMedicar.setOnClickListener(v -> {
                Intent intent = new Intent(EnfermeroInicioActivity.this, AdministrarMedicamentoActivity.class);
                startActivity(intent);
            });
        }
    }

    private void configurarBottomNav() {
        if (bottomNavEnfermero == null) return;
        bottomNavEnfermero.setSelectedItemId(R.id.nav_turno);
        bottomNavEnfermero.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_turno) {
                return true;
            } else if (itemId == R.id.nav_signos) {
                startActivity(new Intent(EnfermeroInicioActivity.this, SignosVitalesActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_medicacion) {
                startActivity(new Intent(EnfermeroInicioActivity.this, AdministrarMedicamentoActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void actualizarMetricas() {
        if (tvPacientesAsignados != null) {
            tvPacientesAsignados.setText("1");
        }
        if (tvRegistrosHechos != null) {
            tvRegistrosHechos.setText(String.valueOf(REGISTROS_HECHOS));
        }
    }
}