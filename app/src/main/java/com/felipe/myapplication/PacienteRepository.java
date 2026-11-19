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
            long result;
            try { result = pacienteDao.insertPaciente(p); }
            catch (RuntimeException ex) { android.util.Log.e("PacienteRepository", "Error al insertar", ex); result = -1; }
            if (callback != null) {
                final long resultadoFinal = result;
                mainHandler.post(() -> callback.onComplete(resultadoFinal));
            }
        });
    }

    public void obtenerTodosLosPacientes(RepositoryCallback<List<Paciente>> callback) {
        executor.execute(() -> {
            List<Paciente> result;
            try { result = pacienteDao.obtenerTodosLosPacientes(); }
            catch (RuntimeException ex) {
                android.util.Log.e("PacienteRepository", "Error al listar", ex);
                result = null;
            }
            final List<Paciente> resultadoFinal = result;
            if (callback != null) {
                mainHandler.post(() -> callback.onComplete(resultadoFinal));
            }
        });
    }

    public void obtenerPacientePorId(long id, RepositoryCallback<Paciente> callback) {
        executor.execute(() -> {
            Paciente result;
            try { result = pacienteDao.obtenerPacientePorId(id); }
            catch (RuntimeException ex) {
                android.util.Log.e("PacienteRepository", "Error al buscar paciente", ex);
                result = null;
            }
            final Paciente resultadoFinal = result;
            if (callback != null) {
                mainHandler.post(() -> callback.onComplete(resultadoFinal));
            }
        });
    }

    public void actualizarPaciente(Paciente p, RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            int result;
            try { result = pacienteDao.actualizarPaciente(p); }
            catch (RuntimeException ex) { android.util.Log.e("PacienteRepository", "Error al actualizar", ex); result = -1; }
            if (callback != null) {
                final int resultadoFinal = result;
                mainHandler.post(() -> callback.onComplete(resultadoFinal));
            }
        });
    }

    public void eliminarPaciente(long id, RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            int result;
            try { result = pacienteDao.eliminarPaciente(id); }
            catch (RuntimeException ex) { android.util.Log.e("PacienteRepository", "Error al eliminar", ex); result = -1; }
            if (callback != null) {
                final int resultadoFinal = result;
                mainHandler.post(() -> callback.onComplete(resultadoFinal));
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
