package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final CentroMedico CENTRO_MEDICO = new CentroMedico("Clínica San Rafael");
    private static final int REQUEST_CONTRATAR = 101;

    private MaterialToolbar toolbarMain;
    private ChipGroup chipGroupFiltro;
    private RecyclerView rvPersonal;
    private EmpleadoAdapter adapter;

    // Vista de detalle
    private TextView tvDetalleAvatar;
    private TextView tvDetalleNombre;
    private TextView tvDetalleSubtitulo;
    private TextView tvDetalleFechaIngreso;
    private TextView tvDetalleSueldo;

    private MaterialButton btnAbrirContratar;
    private MaterialButton btnAbrirRegistrarPaciente;
    private BottomNavigationView bottomNavMain;

    // Lista en memoria local para sincronizar vistas
    private final List<Empleado> listaEmpleados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarDatosPredeterminados();
        inicializarVistas();
        configurarRecyclerView();
        configurarFiltros();
        configurarBotones();
        configurarBottomNav();

        actualizarListaYHeader();
    }

    private void inicializarDatosPredeterminados() {
        if (listaEmpleados.isEmpty()) {
            Medico medico1 = new Medico(
                    "Camila", "Cifuentes", "CC", "12345678A", 42, "Colombia", 1.70,
                    "MED-001", 4500000.0, "Cardiología", "COL-98765"
            );
            medico1.setFechaIngreso("2018-03-15");

            Enfermero enfermero1 = new Enfermero(
                    "Felipe", "Romo", "CC", "87654321B", 35, "Colombia", 1.75,
                    "EMP-014", 2200000.0, "Mañana"
            );
            enfermero1.setFechaIngreso("2020-06-01");

            Administrativo admin1 = new Administrativo(
                    "Claudia", "Espinoza", "CC", "11223344C", 29, "Colombia", 1.65,
                    "EMP-007", 1800000.0, "Recepción"
            );
            admin1.setFechaIngreso("2021-01-10");

            CENTRO_MEDICO.contratar(medico1);
            CENTRO_MEDICO.contratar(enfermero1);
            CENTRO_MEDICO.contratar(admin1);

            listaEmpleados.add(medico1);
            listaEmpleados.add(enfermero1);
            listaEmpleados.add(admin1);
        }
    }

    private void inicializarVistas() {
        toolbarMain = findViewById(R.id.toolbarMain);
        chipGroupFiltro = findViewById(R.id.chipGroupFiltro);
        rvPersonal = findViewById(R.id.rvPersonal);

        tvDetalleAvatar = findViewById(R.id.tvDetalleAvatar);
        tvDetalleNombre = findViewById(R.id.tvDetalleNombre);
        tvDetalleSubtitulo = findViewById(R.id.tvDetalleSubtitulo);
        tvDetalleFechaIngreso = findViewById(R.id.tvDetalleFechaIngreso);
        tvDetalleSueldo = findViewById(R.id.tvDetalleSueldo);

        btnAbrirContratar = findViewById(R.id.btnAbrirContratar);
        btnAbrirRegistrarPaciente = findViewById(R.id.btnAbrirRegistrarPaciente);
        bottomNavMain = findViewById(R.id.bottomNavMain);
    }

    private void configurarRecyclerView() {
        rvPersonal.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmpleadoAdapter(listaEmpleados, this::mostrarDetalleEmpleado);
        rvPersonal.setAdapter(adapter);

        if (!listaEmpleados.isEmpty()) {
            mostrarDetalleEmpleado(listaEmpleados.get(0));
        }
    }

    private void configurarFiltros() {
        chipGroupFiltro.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int checkedId = checkedIds.get(0);

            if (checkedId == R.id.chipMedicos) {
                adapter.filtrarPorRol("Médicos");
            } else if (checkedId == R.id.chipEnfermeros) {
                adapter.filtrarPorRol("Enfermeros");
            } else if (checkedId == R.id.chipAdmin) {
                adapter.filtrarPorRol("Administrativos");
            } else {
                adapter.filtrarPorRol("Todos");
            }
        });
    }

    private void configurarBotones() {
        btnAbrirContratar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContratarEmpleado.class);
            startActivityForResult(intent, REQUEST_CONTRATAR);
        });

        btnAbrirRegistrarPaciente.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrarPaciente.class);
            startActivity(intent);
        });
    }

    private void configurarBottomNav() {
        if (bottomNavMain == null) return;
        bottomNavMain.setSelectedItemId(R.id.nav_personal);
        bottomNavMain.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_inicio) {
                startActivity(new Intent(MainActivity.this, AdminInicioActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_pacientes) {
                startActivity(new Intent(MainActivity.this, PacientesActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_agendar) {
                startActivity(new Intent(MainActivity.this, AgendarCitaActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_personal) {
                return true;
            }
            return false;
        });
    }

    private void mostrarDetalleEmpleado(Empleado emp) {
        if (emp == null) return;

        String iniN = (emp.getNombre() != null && !emp.getNombre().isEmpty()) ? emp.getNombre().substring(0, 1).toUpperCase() : "E";
        String iniA = (emp.getApellido() != null && !emp.getApellido().isEmpty()) ? emp.getApellido().substring(0, 1).toUpperCase() : "";
        tvDetalleAvatar.setText(iniN + iniA);

        tvDetalleNombre.setText(emp.getNombre() + " " + emp.getApellido());

        if (emp instanceof Medico) {
            Medico m = (Medico) emp;
            tvDetalleSubtitulo.setText("Cardiología · N.º colegiado " + m.getNumeroColegiado());
        } else if (emp instanceof Enfermero) {
            Enfermero e = (Enfermero) emp;
            tvDetalleSubtitulo.setText("Enfermero · Turno " + e.getTurno());
        } else if (emp instanceof Administrativo) {
            Administrativo a = (Administrativo) emp;
            tvDetalleSubtitulo.setText("Administrativo · " + a.getArea());
        } else {
            tvDetalleSubtitulo.setText("Empleado");
        }

        tvDetalleFechaIngreso.setText(emp.getFechaIngreso() != null && !emp.getFechaIngreso().isEmpty() ? emp.getFechaIngreso() : "2026-01-15");

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        tvDetalleSueldo.setText(format.format(emp.getSalario()) + " COP");
    }

    private void actualizarListaYHeader() {
        int total = listaEmpleados.size();
        toolbarMain.setSubtitle("Clínica San Rafael · " + total + " empleados");
        adapter.setEmpleados(listaEmpleados);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarListaYHeader();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONTRATAR && resultCode == RESULT_OK) {
            actualizarListaYHeader();
        }
    }
}