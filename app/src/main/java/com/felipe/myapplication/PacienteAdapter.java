package com.felipe.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {

    public interface OnPacienteClickListener {
        void onPacienteClick(Paciente paciente);
    }

    public interface OnPacienteEditListener {
        void onPacienteEdit(Paciente paciente);
    }

    public interface OnPacienteDeleteListener {
        void onPacienteDelete(Paciente paciente);
    }

    private final List<Paciente> listaCompleta = new ArrayList<>();
    private final List<Paciente> listaFiltrada = new ArrayList<>();
    private final OnPacienteClickListener listener;
    private OnPacienteEditListener editListener;
    private OnPacienteDeleteListener deleteListener;

    public PacienteAdapter(List<Paciente> pacientes, OnPacienteClickListener listener) {
        this.listener = listener;
        setPacientes(pacientes);
    }

    public PacienteAdapter(List<Paciente> pacientes, OnPacienteClickListener listener,
                           OnPacienteEditListener editListener, OnPacienteDeleteListener deleteListener) {
        this.listener = listener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
        setPacientes(pacientes);
    }

    public void setOnPacienteEditListener(OnPacienteEditListener editListener) {
        this.editListener = editListener;
    }

    public void setOnPacienteDeleteListener(OnPacienteDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setPacientes(List<Paciente> pacientes) {
        listaCompleta.clear();
        if (pacientes != null) {
            listaCompleta.addAll(pacientes);
        }
        filtrar("");
    }

    public void filtrar(String query) {
        listaFiltrada.clear();
        if (query == null || query.trim().isEmpty()) {
            listaFiltrada.addAll(listaCompleta);
        } else {
            String q = query.toLowerCase().trim();
            for (Paciente p : listaCompleta) {
                if (p == null) continue;
                String nom = p.getNombre() != null ? p.getNombre() : "";
                String ape = p.getApellido() != null ? p.getApellido() : "";
                String nombreCompleto = (nom + " " + ape).toLowerCase();
                String dni = p.getIdentidad() != null ? p.getIdentidad().toLowerCase() : "";
                String historia = p.getNumeroHistoria() != null ? p.getNumeroHistoria().toLowerCase() : "";

                if (nombreCompleto.contains(q) || dni.contains(q) || historia.contains(q)) {
                    listaFiltrada.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    public boolean estaVacia() {
        return listaFiltrada.isEmpty();
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paciente, parent, false);
        return new PacienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        Paciente paciente = listaFiltrada.get(position);
        holder.bind(paciente, listener, editListener, deleteListener);
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    static class PacienteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAvatar;
        private final TextView tvItemPaciente;
        private final TextView tvItemHistoria;
        private final ImageButton btnEditarPaciente;
        private final ImageButton btnEliminarPaciente;

        public PacienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            tvItemPaciente = itemView.findViewById(R.id.tvItemPaciente);
            tvItemHistoria = itemView.findViewById(R.id.tvItemHistoria);
            btnEditarPaciente = itemView.findViewById(R.id.btnEditarPaciente);
            btnEliminarPaciente = itemView.findViewById(R.id.btnEliminarPaciente);
        }

        public void bind(Paciente p, OnPacienteClickListener listener,
                         OnPacienteEditListener editListener, OnPacienteDeleteListener deleteListener) {
            if (p == null) return;

            if (tvAvatar != null) {
                tvAvatar.setText(obtenerIniciales(p.getNombre(), p.getApellido()));
            }

            String nom = p.getNombre() != null ? p.getNombre() : "";
            String ape = p.getApellido() != null ? p.getApellido() : "";
            String nombreStr = (nom + " " + ape).trim();
            if (p.getEdad() > 0) {
                nombreStr += " (" + p.getEdad() + " años)";
            }
            if (tvItemPaciente != null) {
                tvItemPaciente.setText(nombreStr);
            }

            StringBuilder historiaStr = new StringBuilder();
            if (p.getNumeroHistoria() != null && !p.getNumeroHistoria().isEmpty()) {
                historiaStr.append("Historia: ").append(p.getNumeroHistoria());
            }
            if (p.getIdentidad() != null && !p.getIdentidad().isEmpty()) {
                if (historiaStr.length() > 0) {
                    historiaStr.append(" | ");
                }
                historiaStr.append("DNI: ").append(p.getIdentidad());
            }
            if (tvItemHistoria != null) {
                tvItemHistoria.setText(historiaStr.toString());
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPacienteClick(p);
                }
            });

            if (btnEditarPaciente != null) {
                btnEditarPaciente.setOnClickListener(v -> {
                    if (editListener != null) {
                        editListener.onPacienteEdit(p);
                    }
                });
            }

            if (btnEliminarPaciente != null) {
                btnEliminarPaciente.setOnClickListener(v -> {
                    if (deleteListener != null) {
                        deleteListener.onPacienteDelete(p);
                    }
                });
            }
        }

        private String obtenerIniciales(String nombre, String apellido) {
            String iniN = (nombre != null && !nombre.isEmpty()) ? nombre.substring(0, 1).toUpperCase() : "P";
            String iniA = (apellido != null && !apellido.isEmpty()) ? apellido.substring(0, 1).toUpperCase() : "";
            return iniN + iniA;
        }
    }
}
