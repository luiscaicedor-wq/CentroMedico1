package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarPaciente extends AppCompatActivity {

    private TextInputEditText etNombre;
    private TextInputEditText etApellido;
    private TextInputEditText etDni;
    private TextInputEditText etEdad;
    private TextInputEditText etNumeroHistoria;

    // Centro temporal para la sesión actual de esta pantalla.
    private static final CentroMedico CENTRO_MEDICO = new CentroMedico("Clínica San Rafael");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_paciente2);

        inicializarVistas();
        configurarToolbar();
        configurarBotones();
    }

    private void inicializarVistas() {
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etDni = findViewById(R.id.etDni);
        etEdad = findViewById(R.id.etEdad);
        etNumeroHistoria = findViewById(R.id.etNumeroHistoria);
    }

    private void configurarToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void configurarBotones() {
        MaterialButton btnCancelar = findViewById(R.id.btnCancelar);
        MaterialButton btnRegistrar = findViewById(R.id.btnRegistrar);

        btnCancelar.setOnClickListener(v -> finish());
        btnRegistrar.setOnClickListener(v -> registrarPaciente());
    }

    private void registrarPaciente() {
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

        // Se crea el paciente con historial médico vacío automáticamente.
        Paciente paciente = new Paciente(
                nombre,
                apellido,
                "CC",
                dni,
                edad,
                "Colombia",
                1.70,
                numeroHistoria
        );

        CENTRO_MEDICO.registrarPaciente(paciente);
        MainActivity.CENTRO_MEDICO.registrarPaciente(paciente);
        PacientesActivity.LISTA_PACIENTES.add(paciente);

        Toast.makeText(
                this,
                "Paciente registrado correctamente",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(this, PacientesActivity.class);
        startActivity(intent);

        setResult(RESULT_OK);
        finish();
    }

    private String obtenerTexto(TextInputEditText editText) {
        if (editText.getText() == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }
}