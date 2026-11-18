package com.felipe.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

public class ContratarEmpleado extends AppCompatActivity {

    private MaterialButtonToggleGroup toggleGroupRol;
    private TextInputEditText etNombre;
    private TextInputEditText etApellido;
    private TextInputEditText etDni;
    private TextInputEditText etEdad;
    private TextInputEditText etIdEmpleado;
    private TextInputEditText etFechaIngreso;
    private TextInputEditText etSueldoBase;

    // Campos dinámicos
    private View layoutCamposMedico;
    private TextInputEditText etEspecialidad;
    private TextInputEditText etNumeroColegiado;

    private View layoutCamposEnfermero;
    private MaterialAutoCompleteTextView acTurno;

    private View layoutCamposAdmin;
    private TextInputEditText etArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar_empleado);

        inicializarVistas();
        configurarToolbar();
        configurarToggleRol();
        configurarDropdownTurno();

        MaterialButton btnContratar = findViewById(R.id.btnContratarEmpleado);
        btnContratar.setOnClickListener(v -> realizarContratacion());
    }

    private void inicializarVistas() {
        toggleGroupRol = findViewById(R.id.toggleGroupRol);
        etNombre = findViewById(R.id.etNombreEmpleado);
        etApellido = findViewById(R.id.etApellidoEmpleado);
        etDni = findViewById(R.id.etDniEmpleado);
        etEdad = findViewById(R.id.etEdadEmpleado);
        etIdEmpleado = findViewById(R.id.etIdEmpleado);
        etFechaIngreso = findViewById(R.id.etFechaIngreso);
        etSueldoBase = findViewById(R.id.etSueldoBase);

        layoutCamposMedico = findViewById(R.id.layoutCamposMedico);
        etEspecialidad = findViewById(R.id.etEspecialidad);
        etNumeroColegiado = findViewById(R.id.etNumeroColegiado);

        layoutCamposEnfermero = findViewById(R.id.layoutCamposEnfermero);
        acTurno = findViewById(R.id.acTurno);

        layoutCamposAdmin = findViewById(R.id.layoutCamposAdmin);
        etArea = findViewById(R.id.etArea);
    }

    private void configurarToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarContratar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void configurarToggleRol() {
        toggleGroupRol.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnToggleMedico) {
                    mostrarCampos(layoutCamposMedico);
                } else if (checkedId == R.id.btnToggleEnfermero) {
                    mostrarCampos(layoutCamposEnfermero);
                } else if (checkedId == R.id.btnToggleAdmin) {
                    mostrarCampos(layoutCamposAdmin);
                }
            }
        });
    }

    private void mostrarCampos(View campoVisible) {
        layoutCamposMedico.setVisibility(View.GONE);
        layoutCamposEnfermero.setVisibility(View.GONE);
        layoutCamposAdmin.setVisibility(View.GONE);

        campoVisible.setVisibility(View.VISIBLE);
    }

    private void configurarDropdownTurno() {
        String[] turnos = new String[]{"Mañana", "Tarde", "Noche"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, turnos);
        acTurno.setAdapter(adapter);
        acTurno.setText(turnos[0], false);
    }

    private void realizarContratacion() {
        String nombre = obtenerTexto(etNombre);
        String apellido = obtenerTexto(etApellido);
        String dni = obtenerTexto(etDni);
        String edadTxt = obtenerTexto(etEdad);
        String idEmpGenerado = obtenerTexto(etIdEmpleado);
        String fechaIngreso = obtenerTexto(etFechaIngreso);
        String sueldoTxt = obtenerTexto(etSueldoBase);

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

        int edad = 30;
        try {
            if (!TextUtils.isEmpty(edadTxt)) {
                edad = Integer.parseInt(edadTxt);
            }
        } catch (NumberFormatException ignored) {}

        double sueldo = 3000000.0;
        try {
            if (!TextUtils.isEmpty(sueldoTxt)) {
                sueldo = Double.parseDouble(sueldoTxt);
            }
        } catch (NumberFormatException ignored) {}

        if (TextUtils.isEmpty(idEmpGenerado)) {
            idEmpGenerado = "EMP-" + (System.currentTimeMillis() % 10000);
        }

        if (TextUtils.isEmpty(fechaIngreso)) {
            fechaIngreso = "2026-01-15";
        }

        Empleado nuevoEmpleado;
        int checkedRol = toggleGroupRol.getCheckedButtonId();

        if (checkedRol == R.id.btnToggleEnfermero) {
            String turno = acTurno.getText().toString();
            Enfermero enf = new Enfermero(nombre, apellido, "CC", dni, edad, "Colombia", 1.70, idEmpGenerado, sueldo, turno);
            enf.setFechaIngreso(fechaIngreso);
            nuevoEmpleado = enf;
        } else if (checkedRol == R.id.btnToggleAdmin) {
            String area = obtenerTexto(etArea);
            if (TextUtils.isEmpty(area)) {
                area = "Recepción";
            }
            Administrativo adm = new Administrativo(nombre, apellido, "CC", dni, edad, "Colombia", 1.70, idEmpGenerado, sueldo, area);
            adm.setFechaIngreso(fechaIngreso);
            nuevoEmpleado = adm;
        } else {
            // Por defecto: Médico
            String especialidad = obtenerTexto(etEspecialidad);
            String colegiado = obtenerTexto(etNumeroColegiado);
            if (TextUtils.isEmpty(especialidad)) especialidad = "Medicina General";
            if (TextUtils.isEmpty(colegiado)) colegiado = "COL-" + (10000 + (int)(Math.random() * 89999));

            Medico med = new Medico(nombre, apellido, "CC", dni, edad, "Colombia", 1.75, idEmpGenerado, sueldo, especialidad, colegiado);
            med.setFechaIngreso(fechaIngreso);
            nuevoEmpleado = med;
        }

        // Agregar al centro médico
        MainActivity.CENTRO_MEDICO.contratar(nuevoEmpleado);

        Toast.makeText(this, "Empleado contratado exitosamente", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private String obtenerTexto(TextInputEditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }
}