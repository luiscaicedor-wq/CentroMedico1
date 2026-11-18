package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private TextInputEditText edtUsuario;
    private TextInputEditText edtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarVistas();
        configurarEventos();
    }

    private void inicializarVistas() {
        edtUsuario = findViewById(R.id.edtUsuario);
        edtContrasena = findViewById(R.id.edtContrasena);
    }

    private void configurarEventos() {
        MaterialButton btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        View cardRolMedico = findViewById(R.id.cardRolMedico);
        View cardRolEnfermero = findViewById(R.id.cardRolEnfermero);
        View cardRolAdmin = findViewById(R.id.cardRolAdmin);

        if (btnIniciarSesion != null) {
            btnIniciarSesion.setOnClickListener(v -> {
                String usuario = obtenerTexto(edtUsuario);
                String contrasena = obtenerTexto(edtContrasena);
                if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(contrasena)) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                }
                // Al presionar INICIAR SESIÓN, envía al panel del inicio con todas las opciones (Pacientes, Citas, Personal)
                Intent intent = new Intent(Login.this, AdminInicioActivity.class);
                startActivity(intent);
                finish();
            });
        }


        if (cardRolMedico != null) {
            cardRolMedico.setOnClickListener(v -> ingresarAApp("Médicos"));
        }

        if (cardRolEnfermero != null) {
            cardRolEnfermero.setOnClickListener(v -> ingresarAApp("Enfermeros"));
        }

        if (cardRolAdmin != null) {
            cardRolAdmin.setOnClickListener(v -> ingresarAApp("Administrativos"));
        }
    }

    private void ingresarAApp(String rolSeleccionado) {
        Intent intent;
        if ("Administrativos".equalsIgnoreCase(rolSeleccionado) || "Administrativo".equalsIgnoreCase(rolSeleccionado)) {
            intent = new Intent(Login.this, AdminInicioActivity.class);
        } else if ("Enfermeros".equalsIgnoreCase(rolSeleccionado) || "Enfermero".equalsIgnoreCase(rolSeleccionado)) {
            intent = new Intent(Login.this, EnfermeroInicioActivity.class);
        } else if ("Médicos".equalsIgnoreCase(rolSeleccionado) || "Médico".equalsIgnoreCase(rolSeleccionado)) {
            intent = new Intent(Login.this, MedicoInicioActivity.class);
        } else {
            intent = new Intent(Login.this, MainActivity.class);
        }
        intent.putExtra("ROL_SELECCIONADO", rolSeleccionado);
        startActivity(intent);
        finish();
    }

    private String obtenerTexto(TextInputEditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }
}