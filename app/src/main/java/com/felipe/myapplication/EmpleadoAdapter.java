package com.felipe.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder> {

    public interface OnEmpleadoClickListener {
        void onEmpleadoClick(Empleado empleado);
    }

    private final List<Empleado> listaCompleta = new ArrayList<>();
    private final List<Empleado> listaFiltrada = new ArrayList<>();
    private final OnEmpleadoClickListener listener;

    public EmpleadoAdapter(List<Empleado> empleados, OnEmpleadoClickListener listener) {
        this.listener = listener;
        setEmpleados(empleados);
    }

    public void setEmpleados(List<Empleado> empleados) {
        listaCompleta.clear();
        if (empleados != null) {
            listaCompleta.addAll(empleados);
        }
        filtrarPorRol("Todos");
    }

    public void filtrarPorRol(String rol) {
        listaFiltrada.clear();
        if ("Todos".equalsIgnoreCase(rol)) {
            listaFiltrada.addAll(listaCompleta);
        } else {
            for (Empleado emp : listaCompleta) {
                if ("Médicos".equalsIgnoreCase(rol) || "Médico".equalsIgnoreCase(rol)) {
                    if (emp instanceof Medico) {
                        listaFiltrada.add(emp);
                    }
                } else if ("Enfermeros".equalsIgnoreCase(rol) || "Enfermero".equalsIgnoreCase(rol)) {
                    if (emp instanceof Enfermero) {
                        listaFiltrada.add(emp);
                    }
                } else if ("Administrativos".equalsIgnoreCase(rol) || "Administrativo".equalsIgnoreCase(rol)) {
                    if (emp instanceof Administrativo) {
                        listaFiltrada.add(emp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_empleado, parent, false);
        return new EmpleadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoViewHolder holder, int position) {
        Empleado emp = listaFiltrada.get(position);
        holder.bind(emp, listener);
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    static class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAvatar;
        private final TextView tvNombreEmpleado;
        private final TextView tvRolDetalle;
        private final TextView tvIdentificadorExtra;

        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            tvNombreEmpleado = itemView.findViewById(R.id.tvNombreEmpleado);
            tvRolDetalle = itemView.findViewById(R.id.tvRolDetalle);
            tvIdentificadorExtra = itemView.findViewById(R.id.tvIdentificadorExtra);
        }

        public void bind(Empleado emp, OnEmpleadoClickListener listener) {
            String iniciales = obtenerIniciales(emp.getNombre(), emp.getApellido());
            tvAvatar.setText(iniciales);
            tvNombreEmpleado.setText(emp.getNombre() + " " + emp.getApellido());

            if (emp instanceof Medico) {
                Medico m = (Medico) emp;
                tvRolDetalle.setText("Médico · " + m.getEspecialidad());
                tvIdentificadorExtra.setText(m.getNumeroColegiado());
            } else if (emp instanceof Enfermero) {
                Enfermero e = (Enfermero) emp;
                tvRolDetalle.setText("Enfermero · Turno " + e.getTurno());
                tvIdentificadorExtra.setText(e.getId_empleado());
            } else if (emp instanceof Administrativo) {
                Administrativo a = (Administrativo) emp;
                tvRolDetalle.setText("Administrativo · " + a.getArea());
                tvIdentificadorExtra.setText(a.getId_empleado());
            } else {
                tvRolDetalle.setText("Empleado");
                tvIdentificadorExtra.setText(emp.getId_empleado());
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEmpleadoClick(emp);
                }
            });
        }

        private String obtenerIniciales(String nombre, String apellido) {
            String iniN = (nombre != null && !nombre.isEmpty()) ? nombre.substring(0, 1).toUpperCase() : "E";
            String iniA = (apellido != null && !apellido.isEmpty()) ? apellido.substring(0, 1).toUpperCase() : "";
            return iniN + iniA;
        }
    }
}