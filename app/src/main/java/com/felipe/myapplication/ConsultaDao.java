package com.felipe.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/** Acceso a datos de consultas; no debe ejecutarse en el hilo de UI. */
public class ConsultaDao {
    private final DatabaseHelper helper;
    public ConsultaDao(Context context) { helper = DatabaseHelper.getInstance(context); }

    public long insertar(String fecha, String motivo, String medico, long pacienteId) {
        ContentValues v = new ContentValues();
        v.put(DatabaseHelper.COLUMN_CONSULTA_FECHA, fecha);
        v.put(DatabaseHelper.COLUMN_CONSULTA_MOTIVO, motivo);
        v.put(DatabaseHelper.COLUMN_CONSULTA_MEDICO, medico);
        v.put(DatabaseHelper.COLUMN_CONSULTA_PACIENTE_ID, pacienteId);
        return helper.getWritableDatabase().insert(DatabaseHelper.TABLE_CONSULTAS, null, v);
    }

    public List<ConsultaRegistro> listar() {
        List<ConsultaRegistro> result = new ArrayList<>();
        String sql = "SELECT c.id, c.fecha, c.motivo, c.medico, " +
                "p.nombre || ' ' || p.apellido AS paciente " +
                "FROM consultas c JOIN pacientes p ON p.id = c.paciente_id ORDER BY c.id DESC";
        try (Cursor c = helper.getReadableDatabase().rawQuery(sql, null)) {
            while (c.moveToNext()) {
                result.add(new ConsultaRegistro(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
            }
        }
        return result;
    }

    public int contar() {
        try (Cursor c = helper.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM consultas", null)) {
            return c.moveToFirst() ? c.getInt(0) : 0;
        }
    }

    public int eliminar(long id) {
        return helper.getWritableDatabase().delete(DatabaseHelper.TABLE_CONSULTAS, "id = ?", new String[]{String.valueOf(id)});
    }
}
