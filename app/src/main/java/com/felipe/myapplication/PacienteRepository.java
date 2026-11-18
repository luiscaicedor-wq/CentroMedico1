package com.felipe.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PacienteRepository {

    public interface RepositoryCallback<T> {
        void onComplete(T result);
    }

    private final PacienteDao pacienteDao;
    private final ExecutorService executor;
    private final Handler mainHandler;

    public PacienteRepository(Context context) {
        this.pacienteDao = new PacienteDao(context);
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public PacienteRepository(PacienteDao pacienteDao) {
        this.pacienteDao = pacienteDao;
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void insertPaciente(Paciente p, RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            long result = pacienteDao.insertPaciente(p);
            if (callback != null) {
                mainHandler.post(() -> callback.onComplete(result));
            }
        });
    }

    public void obtenerTodosLosPacientes(RepositoryCallback<List<Paciente>> callback) {
        executor.execute(() -> {
            List<Paciente> result = pacienteDao.obtenerTodosLosPacientes();
            if (callback != null) {
                mainHandler.post(() -> callback.onComplete(result));
            }
        });
    }

    public void obtenerPacientePorId(long id, RepositoryCallback<Paciente> callback) {
        executor.execute(() -> {
            Paciente result = pacienteDao.obtenerPacientePorId(id);
            if (callback != null) {
                mainHandler.post(() -> callback.onComplete(result));
            }
        });
    }

    public void actualizarPaciente(Paciente p, RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            int result = pacienteDao.actualizarPaciente(p);
            if (callback != null) {
                mainHandler.post(() -> callback.onComplete(result));
            }
        });
    }

    public void eliminarPaciente(long id, RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            int result = pacienteDao.eliminarPaciente(id);
            if (callback != null) {
                mainHandler.post(() -> callback.onComplete(result));
            }
        });
    }

    // Synchronous methods
    public long insertPacienteSync(Paciente p) {
        return pacienteDao.insertPaciente(p);
    }

    public List<Paciente> obtenerTodosLosPacientesSync() {
        return pacienteDao.obtenerTodosLosPacientes();
    }

    public Paciente obtenerPacientePorIdSync(long id) {
        return pacienteDao.obtenerPacientePorId(id);
    }

    public int actualizarPacienteSync(Paciente p) {
        return pacienteDao.actualizarPaciente(p);
    }

    public int eliminarPacienteSync(long id) {
        return pacienteDao.eliminarPaciente(id);
    }
}
