package com.felipe.myapplication;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class ConsultaViewModel extends AndroidViewModel {
    private final ConsultaRepository repository;
    private final MutableLiveData<List<ConsultaRegistro>> consultas = new MutableLiveData<>();
    private final MutableLiveData<Integer> total = new MutableLiveData<>();
    public ConsultaViewModel(@NonNull Application app) {
        super(app);
        repository = new ConsultaRepository(app);
    }
    public LiveData<List<ConsultaRegistro>> getConsultas() { return consultas; }
    public LiveData<Integer> getTotal() { return total; }
    public void cargar() { repository.listar(consultas::setValue); repository.contar(total::setValue); }
    public void guardar(String fecha, String motivo, String medico, long pacienteId, ConsultaRepository.Callback<Long> callback) {
        repository.insertar(fecha, motivo, medico, pacienteId, id -> {
            if (id != -1) cargar();
            callback.complete(id);
        });
    }
    public void eliminar(long id, ConsultaRepository.Callback<Integer> callback) {
        repository.eliminar(id, n -> { if (n > 0) cargar(); callback.complete(n); });
    }
}
