package com.felipe.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Ejecuta operaciones SQLite fuera del hilo principal. */
public class ConsultaRepository {
    public interface Callback<T> { void complete(T result); }
    private final ConsultaDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler ui = new Handler(Looper.getMainLooper());
    public ConsultaRepository(Context context) { dao = new ConsultaDao(context); }
    public void insertar(String fecha, String motivo, String medico, long pacienteId, Callback<Long> callback) {
        executor.execute(() -> {
            long result;
            try { result = dao.insertar(fecha, motivo, medico, pacienteId); }
            catch (RuntimeException ex) { android.util.Log.e("ConsultaRepository", "Error al guardar", ex); result = -1; }
            final long id = result;
            ui.post(() -> callback.complete(id));
        });
    }
    public void listar(Callback<List<ConsultaRegistro>> callback) {
        executor.execute(() -> {
            List<ConsultaRegistro> result;
            try { result = dao.listar(); }
            catch (RuntimeException ex) { android.util.Log.e("ConsultaRepository", "Error al listar", ex); result = null; }
            final List<ConsultaRegistro> records = result;
            ui.post(() -> callback.complete(records));
        });
    }
    public void contar(Callback<Integer> callback) {
        executor.execute(() -> {
            int result;
            try { result = dao.contar(); }
            catch (RuntimeException ex) { android.util.Log.e("ConsultaRepository", "Error al contar", ex); result = -1; }
            final int count = result;
            ui.post(() -> callback.complete(count));
        });
    }
    public void eliminar(long id, Callback<Integer> callback) {
        executor.execute(() -> {
            int result;
            try { result = dao.eliminar(id); }
            catch (RuntimeException ex) { android.util.Log.e("ConsultaRepository", "Error al eliminar", ex); result = -1; }
            final int deleted = result;
            ui.post(() -> callback.complete(deleted));
        });
    }
}
