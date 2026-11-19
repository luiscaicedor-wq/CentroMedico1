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
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import android.widget.LinearLayout;
import android.view.View;

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
    private List<Paciente> pacientesDisponibles = new ArrayList<>();
    private PacienteViewModel pacienteViewModel;
    private ConsultaViewModel consultaViewModel;
    private LinearLayout listaCitas;
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
        pacienteViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);
        consultaViewModel = new ViewModelProvider(this).get(ConsultaViewModel.class);
        listaCitas = findViewById(R.id.listaCitas);
        pacienteViewModel.getPacientes().observe(this, lista -> {
            pacientesDisponibles = lista == null ? new ArrayList<>() : lista;
            configurarPacientes();
        });
        consultaViewModel.getConsultas().observe(this, this::mostrarConsultas);
        configurarDropdowns();
        consultaViewModel.cargar();
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
        configurarPacientes();
        List<String> medicos = new ArrayList<>();
        medicos.add("Dra. Laura Gómez — Cardiología");
        medicos.add("Dr. Carlos Ruiz — Medicina General");
        acMedico.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, medicos));
        acMedico.setText(medicos.get(0), false);
    }

    private void configurarPacientes() {
        String anterior = acPaciente.getText() == null ? "" : acPaciente.getText().toString();
        List<String> nombres = new ArrayList<>();
        for (Paciente p : pacientesDisponibles) {
            nombres.add(p.getNombre() + " " + p.getApellido() + " — " + p.getNumeroHistoria());
        }
        acPaciente.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nombres));
        if (nombres.contains(anterior)) acPaciente.setText(anterior, false);
        else acPaciente.setText(nombres.isEmpty() ? "" : nombres.get(0), false);
    }

    private void mostrarConsultas(List<ConsultaRegistro> consultas) {
        if (listaCitas == null) return;
        listaCitas.removeAllViews();
        if (consultas == null) {
            TextView error = new TextView(this);
            error.setText(R.string.citas_error);
            listaCitas.addView(error);
            return;
        }
        if (consultas.isEmpty()) {
            TextView vacio = new TextView(this);
            vacio.setText(R.string.citas_vacias);
            vacio.setPadding(8, 12, 8, 12);
            listaCitas.addView(vacio);
            return;
        }
        for (ConsultaRegistro c : consultas) {
            TextView item = new TextView(this);
            item.setText(c.fecha + " · " + c.paciente + "\n" + c.medico + "\n" + c.motivo);
            item.setTextSize(15);
            item.setPadding(16, 18, 16, 18);
            item.setBackgroundResource(R.drawable.card_background);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
            params.bottomMargin = (int) (8 * getResources().getDisplayMetrics().density);
            listaCitas.addView(item, params);
            item.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle(R.string.eliminar_cita_titulo)
                    .setMessage(getString(R.string.eliminar_cita_mensaje, c.paciente, c.fecha))
                    .setNegativeButton(R.string.btn_cancelar, null)
                    .setPositiveButton(R.string.btn_eliminar, (dialog, which) ->
                            consultaViewModel.eliminar(c.id, n -> {
                                if (n <= 0) Toast.makeText(this, R.string.citas_error, Toast.LENGTH_LONG).show();
                            })).show());
        }
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
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);
        datePickerDialog.getDatePicker().setMinDate(hoy.getTimeInMillis());
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

        String fecha = etFecha.getText() != null
                ? etFecha.getText().toString().trim() : "";

        String motivo = etMotivo.getText() != null
                ? etMotivo.getText().toString().trim() : "";

        String medicoSeleccionado = acMedico.getText() != null
                ? acMedico.getText().toString().trim() : "";

        String pacienteSeleccionado = acPaciente.getText() != null
                ? acPaciente.getText().toString().trim() : "";

        // Validar fecha
        if (fecha.isEmpty()) {
            etFecha.setError("Selecciona una fecha");
            return;
        }

        // No permitir citas con fecha anterior a hoy.
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            formato.setLenient(false);
            Date elegida = formato.parse(fecha);
            Calendar inicioHoy = Calendar.getInstance();
            inicioHoy.set(Calendar.HOUR_OF_DAY, 0);
            inicioHoy.set(Calendar.MINUTE, 0);
            inicioHoy.set(Calendar.SECOND, 0);
            inicioHoy.set(Calendar.MILLISECOND, 0);
            if (elegida == null || elegida.before(inicioHoy.getTime())) {
                etFecha.setError("Selecciona una fecha válida desde hoy");
                return;
            }
        } catch (java.text.ParseException ex) {
            etFecha.setError("Formato de fecha inválido");
            return;
        }

        // Validar motivo
        if (motivo.isEmpty()) {
            etMotivo.setError("Escribe el motivo de la consulta");
            etMotivo.requestFocus();
            return;
        }

        // Validar médico
        if (medicoSeleccionado.isEmpty()) {
            acMedico.setError("Selecciona un médico");
            return;
        }

        // Buscar el paciente seleccionado
        Paciente paciente = null;

        for (Paciente p : pacientesDisponibles) {

            String nombreCompleto =
                    p.getNombre() + " " + p.getApellido()
                            + " — " + p.getNumeroHistoria();

            if (nombreCompleto.equals(pacienteSeleccionado)) {
                paciente = p;
                break;
            }
        }

        if (paciente == null) {
            acPaciente.setError("Selecciona un paciente válido");
            return;
        }

        // Validar que el médico esté en las opciones disponibles
        if (!medicoSeleccionado.equals("Dra. Laura Gómez — Cardiología")
                && !medicoSeleccionado.equals(
                "Dr. Carlos Ruiz — Medicina General")) {
            acMedico.setError("Selecciona un médico válido");
            return;
        }

        // Guardar fuera del hilo principal mediante ViewModel -> Repository -> DAO.
        btnConfirmarCita.setEnabled(false);
        consultaViewModel.guardar(fecha, motivo, medicoSeleccionado, paciente.getId(), resultado -> {
            btnConfirmarCita.setEnabled(true);
            if (resultado == -1) {
                Toast.makeText(this, R.string.citas_error, Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, R.string.cita_guardada, Toast.LENGTH_LONG).show();
            etMotivo.setText("");
            // Mantener la pantalla abierta para mostrar la cita recién guardada.
        });
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
