package com.felipe.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgendarCitaActivity extends AppCompatActivity {

    public static final List<Consulta> LISTA_CONSULTAS = new ArrayList<>();

    private MaterialAutoCompleteTextView acPaciente;
    private MaterialAutoCompleteTextView acMedico;
    private TextInputLayout tilFecha;
    private TextInputEditText etFecha;
    private TextInputEditText etMotivo;

    private TextView tvResumenCita;
    private TextView tvResumenFechaMotivo;

    private MaterialButton btnConfirmarCita;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        inicializarVistas();
        configurarToolbar();
        configurarDropdowns();
        configurarDatePicker();
        configurarListeners();
        configurarBottomNav();

        actualizarResumen();
    }

    private void inicializarVistas() {
        acPaciente = findViewById(R.id.acPaciente);
        acMedico = findViewById(R.id.acMedico);
        tilFecha = findViewById(R.id.tilFecha);
        etFecha = findViewById(R.id.etFecha);
        etMotivo = findViewById(R.id.etMotivo);

        tvResumenCita = findViewById(R.id.tvResumenCita);
        tvResumenFechaMotivo = findViewById(R.id.tvResumenFechaMotivo);

        btnConfirmarCita = findViewById(R.id.btnConfirmarCita);
        bottomNav = findViewById(R.id.bottomNav);

        if (etFecha != null && (etFecha.getText() == null || etFecha.getText().toString().trim().isEmpty())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            etFecha.setText(sdf.format(new Date()));
        }
    }

    private void configurarToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void configurarDropdowns() {
        // Pacientes
        List<String> nombresPacientes = new ArrayList<>();
        if (!PacientesActivity.LISTA_PACIENTES.isEmpty()) {
            for (Paciente p : PacientesActivity.LISTA_PACIENTES) {
                nombresPacientes.add(p.getNombre() + " " + p.getApellido() + " — " + p.getNumeroHistoria());
            }
        } else {
            nombresPacientes.add("Juan Pérez — HC-2024-001");
        }

        ArrayAdapter<String> adapterPaciente = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nombresPacientes);
        acPaciente.setAdapter(adapterPaciente);
        acPaciente.setText(nombresPacientes.get(0), false);

        // Médicos
        List<String> nombresMedicos = new ArrayList<>();
        nombresMedicos.add("Dra. Laura Gómez — Cardiología");
        nombresMedicos.add("Dr. Carlos Ruiz — Medicina General");

        ArrayAdapter<String> adapterMedico = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nombresMedicos);
        acMedico.setAdapter(adapterMedico);
        acMedico.setText(nombresMedicos.get(0), false);
    }

    private void configurarDatePicker() {
        if (etFecha != null) {
            etFecha.setOnClickListener(v -> abrirSeleccionadorFecha());
        }
        if (tilFecha != null) {
            tilFecha.setEndIconOnClickListener(v -> abrirSeleccionadorFecha());
        }
    }

    private void abrirSeleccionadorFecha() {
        Calendar calendar = Calendar.getInstance();

        if (etFecha != null && etFecha.getText() != null) {
            String fechaActual = etFecha.getText().toString().trim();
            if (!fechaActual.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = sdf.parse(fechaActual);
                    if (date != null) {
                        calendar.setTime(date);
                    }
                } catch (Exception ignored) {}
            }
        }

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fechaFormateada = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                    if (etFecha != null) {
                        etFecha.setText(fechaFormateada);
                    }
                    actualizarResumen();
                },
                anio, mes, dia
        );
        datePickerDialog.show();
    }

    private void configurarListeners() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizarResumen();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        if (etFecha != null) etFecha.addTextChangedListener(watcher);
        if (etMotivo != null) etMotivo.addTextChangedListener(watcher);
        if (acPaciente != null) acPaciente.addTextChangedListener(watcher);
        if (acMedico != null) acMedico.addTextChangedListener(watcher);

        if (btnConfirmarCita != null) {
            btnConfirmarCita.setOnClickListener(v -> confirmarCita());
        }
    }

    private void actualizarResumen() {
        String pac = acPaciente != null && acPaciente.getText() != null ? acPaciente.getText().toString() : "";
        String med = acMedico != null && acMedico.getText() != null ? acMedico.getText().toString() : "";
        String fecha = etFecha != null && etFecha.getText() != null ? etFecha.getText().toString().trim() : "";
        String motivo = etMotivo != null && etMotivo.getText() != null ? etMotivo.getText().toString().trim() : "";

        if (TextUtils.isEmpty(fecha)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            fecha = sdf.format(new Date());
        }
        if (TextUtils.isEmpty(motivo)) motivo = "Consulta médica";

        String nombrePac = pac.contains("—") ? pac.split("—")[0].trim() : pac;
        String nombreMed = med.contains("—") ? med.split("—")[0].trim() : med;

        if (tvResumenCita != null) {
            tvResumenCita.setText(nombrePac + " con " + nombreMed);
        }
        if (tvResumenFechaMotivo != null) {
            tvResumenFechaMotivo.setText(fecha + " · " + motivo);
        }
    }

    private void confirmarCita() {
        String fecha = etFecha != null && etFecha.getText() != null ? etFecha.getText().toString().trim() : "";
        String motivo = etMotivo != null && etMotivo.getText() != null ? etMotivo.getText().toString().trim() : "";

        if (TextUtils.isEmpty(fecha)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            fecha = sdf.format(new Date());
        }
        if (TextUtils.isEmpty(motivo)) motivo = "Dolor en el pecho";

        Paciente p = !PacientesActivity.LISTA_PACIENTES.isEmpty() ? PacientesActivity.LISTA_PACIENTES.get(0) : new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");
        Medico m = new Medico("Laura", "Gómez", "CC", "12345678A", 42, "Colombia", 1.70, "MED-001", 4500000.0, "Cardiología", "COL-98765");

        Consulta c = new Consulta(fecha, motivo, m, p);
        LISTA_CONSULTAS.add(c);
        MainActivity.CENTRO_MEDICO.agendarConsulta(c);

        Toast.makeText(this, "Cita agendada exitosamente para el " + fecha, Toast.LENGTH_SHORT).show();
        finish();
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
