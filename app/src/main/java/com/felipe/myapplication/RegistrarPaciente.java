package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarPaciente extends AppCompatActivity {

    public static final String EXTRA_PACIENTE_ID = "EXTRA_PACIENTE_ID";

    private TextInputEditText etNombre;
    private TextInputEditText etApellido;
    private TextInputEditText etDni;
    private TextInputEditText etEdad;
    private TextInputEditText etNumeroHistoria;
    private MaterialToolbar toolbar;
    private MaterialButton btnRegistrar;

    private PacienteViewModel viewModel;
    private long pacienteIdEditar = -1;

    // Centro temporal para la sesión actual de esta pantalla.
    private static final CentroMedico CENTRO_MEDICO = new CentroMedico("Clínica San Rafael");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_paciente2);

        viewModel = new ViewModelProvider(this).get(PacienteViewModel.class);

        inicializarVistas();
        configurarToolbar();
        comprobarModoEdicion();
        configurarBotones();
    }

    private void inicializarVistas() {
        toolbar = findViewById(R.id.toolbar);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etDni = findViewById(R.id.etDni);
        etEdad = findViewById(R.id.etEdad);
        etNumeroHistoria = findViewById(R.id.etNumeroHistoria);
        btnRegistrar = findViewById(R.id.btnRegistrar);
    }

    private void configurarToolbar() {
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void comprobarModoEdicion() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_PACIENTE_ID)) {
            pacienteIdEditar = getIntent().getLongExtra(EXTRA_PACIENTE_ID, -1);
            if (pacienteIdEditar != -1) {
                if (toolbar != null) {
                    toolbar.setTitle("Editar Paciente");
                }
                if (btnRegistrar != null) {
                    btnRegistrar.setText("Guardar Cambios");
                }
                viewModel.obtenerPacientePorId(pacienteIdEditar, paciente -> {
                    if (paciente != null) {
                        if (etNombre != null) etNombre.setText(paciente.getNombre());
                        if (etApellido != null) etApellido.setText(paciente.getApellido());
                        if (etDni != null) etDni.setText(paciente.getIdentidad());
                        if (etEdad != null) etEdad.setText(String.valueOf(paciente.getEdad()));
                        if (etNumeroHistoria != null) etNumeroHistoria.setText(paciente.getNumeroHistoria());
                    }
                });
            }
        }
    }

    private void configurarBotones() {
        MaterialButton btnCancelar = findViewById(R.id.btnCancelar);

        if (btnCancelar != null) {
            btnCancelar.setOnClickListener(v -> finish());
        }
        if (btnRegistrar != null) {
            btnRegistrar.setOnClickListener(v -> guardarPaciente());
        }
    }

    private void guardarPaciente() {
        String nombre = obtenerTexto(etNombre);
        String apellido = obtenerTexto(etApellido);
        String dni = obtenerTexto(etDni);
        String edadTexto = obtenerTexto(etEdad);
        String numeroHistoria = obtenerTexto(etNumeroHistoria);

        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Ingrese el nombre");
            etNombre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(apellido)) {
            etApellido.setError("Ingrese el apellido");
            etApellido.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(dni)) {
            etDni.setError("Ingrese el DNI");
            etDni.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edadTexto)) {
            etEdad.setError("Ingrese la edad");
            etEdad.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(numeroHistoria)) {
            etNumeroHistoria.setError("Ingrese el número de historia");
            etNumeroHistoria.requestFocus();
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
        } catch (NumberFormatException e) {
            etEdad.setError("Ingrese una edad válida");
            etEdad.requestFocus();
            return;
        }

        if (edad <= 0 || edad > 120) {
            etEdad.setError("La edad debe estar entre 1 y 120 años");
            etEdad.requestFocus();
            return;
        }

        if (pacienteIdEditar != -1) {
            // Editar existente
            Paciente pacienteEditado = new Paciente(
                    pacienteIdEditar,
                    nombre,
                    apellido,
                    "CC",
                    dni,
                    edad,
                    "Colombia",
                    1.70,
                    numeroHistoria
            );

            viewModel.actualizarPaciente(pacienteEditado, filas -> {
                Toast.makeText(this, "Paciente actualizado correctamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            });
        } else {
            // Crear nuevo
            Paciente nuevoPaciente = new Paciente(
                    nombre,
                    apellido,
                    "CC",
                    dni,
                    edad,
                    "Colombia",
                    1.70,
                    numeroHistoria
            );

            CENTRO_MEDICO.registrarPaciente(nuevoPaciente);
            MainActivity.CENTRO_MEDICO.registrarPaciente(nuevoPaciente);

            viewModel.insertPaciente(nuevoPaciente, id -> {
                nuevoPaciente.setId(id);
                PacientesActivity.LISTA_PACIENTES.add(nuevoPaciente);

                Toast.makeText(this, "Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, PacientesActivity.class);
                startActivity(intent);

                setResult(RESULT_OK);
                finish();
            });
        }
    }

    private String obtenerTexto(TextInputEditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }
}
