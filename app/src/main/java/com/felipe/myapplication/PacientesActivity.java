package com.felipe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PacientesActivity extends AppCompatActivity {

    public static final List<Paciente> LISTA_PACIENTES = new ArrayList<>();

    static {
        if (LISTA_PACIENTES.isEmpty()) {
            Paciente p1 = new Paciente("Juan", "Pérez", "CC", "99887766D", 55, "Colombia", 1.75, "HC-2024-001");
            LISTA_PACIENTES.add(p1);
        }
    }

    private MaterialToolbar toolbar;
    private TextInputEditText etBuscarPaciente;
    private RecyclerView rvPacientes;
    private TextView tvSinPacientes;
    private ExtendedFloatingActionButton fabRegistrarPaciente;
    private BottomNavigationView bottomNav;
    private PacienteAdapter adapter;
    private PacienteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        viewModel = new ViewModelProvider(this).get(PacienteViewModel.class);

        inicializarVistas();
        configurarToolbar();
        configurarRecyclerView();
        observarViewModel();
        configurarEventos();
        configurarBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.cargarPacientes();
        }
    }

    private void inicializarVistas() {
        toolbar = findViewById(R.id.toolbar);
        etBuscarPaciente = findViewById(R.id.etBuscarPaciente);
        rvPacientes = findViewById(R.id.rvPacientes);
        tvSinPacientes = findViewById(R.id.tvSinPacientes);
        fabRegistrarPaciente = findViewById(R.id.fabRegistrarPaciente);
        bottomNav = findViewById(R.id.bottomNav);
    }

    private void configurarToolbar() {
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void configurarRecyclerView() {
        if (rvPacientes != null) {
            rvPacientes.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PacienteAdapter(
                    LISTA_PACIENTES,
                    this::mostrarDetallePaciente,
                    this::editarPaciente,
                    this::confirmarEliminarPaciente
            );
            rvPacientes.setAdapter(adapter);
        }
    }

    private void observarViewModel() {
        if (viewModel != null) {
            viewModel.getPacientesLiveData().observe(this, lista -> {
                if (lista != null) {
                    LISTA_PACIENTES.clear();
                    LISTA_PACIENTES.addAll(lista);
                    actualizarLista();
                }
            });
        }
    }

    private void mostrarDetallePaciente(Paciente paciente) {
        if (paciente == null) return;
        new MaterialAlertDialogBuilder(this)
                .setTitle(paciente.getNombre() + " " + paciente.getApellido())
                .setMessage("DNI: " + paciente.getIdentidad() + "\n" +
                        "Historia Clínica: " + paciente.getNumeroHistoria() + "\n" +
                        "Edad: " + paciente.getEdad() + " años\n" +
                        "Nacionalidad: " + paciente.getNacionalidad() + "\n" +
                        "Estatura: " + paciente.getEstatura() + " m")
                .setPositiveButton("Ver Historial", (dialog, which) -> {
                    Intent intent = new Intent(this, HistorialMedicoActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Cerrar", null)
                .show();
    }

    private void editarPaciente(Paciente paciente) {
        if (paciente == null) return;
        Intent intent = new Intent(this, RegistrarPaciente.class);
        intent.putExtra(RegistrarPaciente.EXTRA_PACIENTE_ID, paciente.getId());
        startActivity(intent);
    }

    private void confirmarEliminarPaciente(Paciente paciente) {
        if (paciente == null) return;
        new MaterialAlertDialogBuilder(this)
                .setTitle("Eliminar Paciente")
                .setMessage("¿Estás seguro de que deseas eliminar a " + paciente.getNombre() + " " + paciente.getApellido() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    if (viewModel != null) {
                        viewModel.eliminarPaciente(paciente.getId(), count -> {
                            Toast.makeText(this, "Paciente eliminado", Toast.LENGTH_SHORT).show();
                            viewModel.cargarPacientes();
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void actualizarLista() {
        if (adapter != null) {
            adapter.setPacientes(LISTA_PACIENTES);
            String query = (etBuscarPaciente != null && etBuscarPaciente.getText() != null)
                    ? etBuscarPaciente.getText().toString() : "";
            adapter.filtrar(query);
            actualizarEstadoVacio();
        }
        if (toolbar != null) {
            toolbar.setSubtitle(LISTA_PACIENTES.size() + " registrado(s)");
        }
    }

    private void actualizarEstadoVacio() {
        boolean estaVacia = (adapter == null || adapter.getItemCount() == 0);
        if (tvSinPacientes != null) {
            tvSinPacientes.setVisibility(estaVacia ? View.VISIBLE : View.GONE);
        }
        if (rvPacientes != null) {
            rvPacientes.setVisibility(estaVacia ? View.GONE : View.VISIBLE);
        }
    }

    private void configurarEventos() {
        if (fabRegistrarPaciente != null) {
            fabRegistrarPaciente.setOnClickListener(v -> {
                Intent intent = new Intent(this, RegistrarPaciente.class);
                startActivity(intent);
            });
        }

        if (etBuscarPaciente != null) {
            etBuscarPaciente.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (adapter != null) {
                        adapter.filtrar(s.toString());
                        actualizarEstadoVacio();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void configurarBottomNav() {
        if (bottomNav == null) return;
        bottomNav.setSelectedItemId(R.id.nav_pacientes);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_inicio) {
                startActivity(new Intent(this, AdminInicioActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_agendar) {
                startActivity(new Intent(this, AgendarCitaActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_pacientes) {
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
