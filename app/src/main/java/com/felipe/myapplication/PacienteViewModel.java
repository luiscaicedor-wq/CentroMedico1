package com.felipe.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class PacienteViewModel extends AndroidViewModel {

    private final PacienteRepository repository;
    private final MutableLiveData<List<Paciente>> pacientesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Paciente> pacienteSeleccionadoLiveData = new MutableLiveData<>();

    public PacienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = new PacienteRepository(application);
        cargarPacientes();
    }

    public PacienteViewModel(@NonNull Application application, PacienteRepository repository) {
        super(application);
        this.repository = repository;
        cargarPacientes();
    }

    public LiveData<List<Paciente>> getPacientesLiveData() {
        return pacientesLiveData;
    }

    public LiveData<List<Paciente>> getPacientes() {
        return pacientesLiveData;
    }

    public LiveData<List<Paciente>> getListaPacientes() {
        return pacientesLiveData;
    }

    public LiveData<Paciente> getPacienteSeleccionadoLiveData() {
        return pacienteSeleccionadoLiveData;
    }

    public void cargarPacientes() {
        repository.obtenerTodosLosPacientes(pacientesLiveData::setValue);
    }

    public void insertPaciente(Paciente p) {
        repository.insertPaciente(p, id -> cargarPacientes());
    }

    public void insertPaciente(Paciente p, PacienteRepository.RepositoryCallback<Long> callback) {
        repository.insertPaciente(p, id -> {
            cargarPacientes();
            if (callback != null) callback.onComplete(id);
        });
    }

    public void insertarPaciente(Paciente p) {
        insertPaciente(p);
    }

    public void actualizarPaciente(Paciente p) {
        repository.actualizarPaciente(p, count -> cargarPacientes());
    }

    public void actualizarPaciente(Paciente p, PacienteRepository.RepositoryCallback<Integer> callback) {
        repository.actualizarPaciente(p, count -> {
            cargarPacientes();
            if (callback != null) callback.onComplete(count);
        });
    }

    public void eliminarPaciente(long id) {
        repository.eliminarPaciente(id, count -> cargarPacientes());
    }

    public void eliminarPaciente(long id, PacienteRepository.RepositoryCallback<Integer> callback) {
        repository.eliminarPaciente(id, count -> {
            cargarPacientes();
            if (callback != null) callback.onComplete(count);
        });
    }

    public void obtenerPacientePorId(long id) {
        repository.obtenerPacientePorId(id, pacienteSeleccionadoLiveData::setValue);
    }

    public void obtenerPacientePorId(long id, PacienteRepository.RepositoryCallback<Paciente> callback) {
        repository.obtenerPacientePorId(id, paciente -> {
            pacienteSeleccionadoLiveData.setValue(paciente);
            if (callback != null) callback.onComplete(paciente);
        });
    }

    public PacienteRepository getRepository() {
        return repository;
    }
}
