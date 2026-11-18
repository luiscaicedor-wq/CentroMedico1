package com.felipe.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PacienteDao {

    private final DatabaseHelper dbHelper;

    public PacienteDao(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public PacienteDao(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertPaciente(Paciente p) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PACIENTE_NOMBRE, p.getNombre());
        values.put(DatabaseHelper.COLUMN_PACIENTE_APELLIDO, p.getApellido());
        values.put(DatabaseHelper.COLUMN_PACIENTE_TIPO_IDENTIDAD, p.getTipoIdentidad());
        values.put(DatabaseHelper.COLUMN_PACIENTE_DNI, p.getDni());
        values.put(DatabaseHelper.COLUMN_PACIENTE_EDAD, p.getEdad());
        values.put(DatabaseHelper.COLUMN_PACIENTE_NACIONALIDAD, p.getNacionalidad());
        values.put(DatabaseHelper.COLUMN_PACIENTE_ESTATURA, p.getEstatura());
        values.put(DatabaseHelper.COLUMN_PACIENTE_NUMERO_HISTORIA, p.getNumeroHistoria());

        long id = db.insert(DatabaseHelper.TABLE_PACIENTES, null, values);
        if (id != -1) {
            p.setId(id);
        }
        return id;
    }

    public List<Paciente> obtenerTodosLosPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor cursor = db.query(
                DatabaseHelper.TABLE_PACIENTES,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_PACIENTE_ID + " ASC"
        )) {
            while (cursor.moveToNext()) {
                pacientes.add(cursorToPaciente(cursor));
            }
        }
        return pacientes;
    }

    public Paciente obtenerPacientePorId(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor cursor = db.query(
                DatabaseHelper.TABLE_PACIENTES,
                null,
                DatabaseHelper.COLUMN_PACIENTE_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                return cursorToPaciente(cursor);
            }
        }
        return null;
    }

    public int actualizarPaciente(Paciente p) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PACIENTE_NOMBRE, p.getNombre());
        values.put(DatabaseHelper.COLUMN_PACIENTE_APELLIDO, p.getApellido());
        values.put(DatabaseHelper.COLUMN_PACIENTE_TIPO_IDENTIDAD, p.getTipoIdentidad());
        values.put(DatabaseHelper.COLUMN_PACIENTE_DNI, p.getDni());
        values.put(DatabaseHelper.COLUMN_PACIENTE_EDAD, p.getEdad());
        values.put(DatabaseHelper.COLUMN_PACIENTE_NACIONALIDAD, p.getNacionalidad());
        values.put(DatabaseHelper.COLUMN_PACIENTE_ESTATURA, p.getEstatura());
        values.put(DatabaseHelper.COLUMN_PACIENTE_NUMERO_HISTORIA, p.getNumeroHistoria());

        return db.update(
                DatabaseHelper.TABLE_PACIENTES,
                values,
                DatabaseHelper.COLUMN_PACIENTE_ID + " = ?",
                new String[]{String.valueOf(p.getId())}
        );
    }

    public int eliminarPaciente(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseHelper.TABLE_PACIENTES,
                DatabaseHelper.COLUMN_PACIENTE_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    private Paciente cursorToPaciente(Cursor cursor) {
        int idIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_ID);
        int nombreIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_NOMBRE);
        int apellidoIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_APELLIDO);
        int tipoIdentidadIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_TIPO_IDENTIDAD);
        int dniIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_DNI);
        int edadIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_EDAD);
        int nacionalidadIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_NACIONALIDAD);
        int estaturaIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_ESTATURA);
        int numeroHistoriaIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PACIENTE_NUMERO_HISTORIA);

        long id = cursor.getLong(idIndex);
        String nombre = cursor.getString(nombreIndex);
        String apellido = cursor.getString(apellidoIndex);
        String tipoIdentidad = cursor.getString(tipoIdentidadIndex);
        String dni = cursor.getString(dniIndex);
        int edad = cursor.getInt(edadIndex);
        String nacionalidad = cursor.getString(nacionalidadIndex);
        double estatura = cursor.getDouble(estaturaIndex);
        String numeroHistoria = cursor.getString(numeroHistoriaIndex);

        return new Paciente(id, nombre, apellido, tipoIdentidad, dni, edad, nacionalidad, estatura, numeroHistoria);
    }
}
