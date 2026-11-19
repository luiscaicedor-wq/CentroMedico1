package com.felipe.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "centro_medico.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla pacientes
    public static final String TABLE_PACIENTES = "pacientes";
    public static final String COLUMN_PACIENTE_ID = "id";
    public static final String COLUMN_PACIENTE_NOMBRE = "nombre";
    public static final String COLUMN_PACIENTE_APELLIDO = "apellido";
    public static final String COLUMN_PACIENTE_TIPO_IDENTIDAD = "tipo_identidad";
    public static final String COLUMN_PACIENTE_DNI = "dni";
    public static final String COLUMN_PACIENTE_EDAD = "edad";
    public static final String COLUMN_PACIENTE_NACIONALIDAD = "nacionalidad";
    public static final String COLUMN_PACIENTE_ESTATURA = "estatura";
    public static final String COLUMN_PACIENTE_NUMERO_HISTORIA = "numero_historia";

    // Tabla consultas
    public static final String TABLE_CONSULTAS = "consultas";
    public static final String COLUMN_CONSULTA_ID = "id";
    public static final String COLUMN_CONSULTA_FECHA = "fecha";
    public static final String COLUMN_CONSULTA_MOTIVO = "motivo";
    public static final String COLUMN_CONSULTA_MEDICO = "medico";
    public static final String COLUMN_CONSULTA_PACIENTE_ID = "paciente_id";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PACIENTES = "CREATE TABLE " + TABLE_PACIENTES + " ("
                + COLUMN_PACIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PACIENTE_NOMBRE + " TEXT, "
                + COLUMN_PACIENTE_APELLIDO + " TEXT, "
                + COLUMN_PACIENTE_TIPO_IDENTIDAD + " TEXT, "
                + COLUMN_PACIENTE_DNI + " TEXT UNIQUE, "
                + COLUMN_PACIENTE_EDAD + " INTEGER, "
                + COLUMN_PACIENTE_NACIONALIDAD + " TEXT, "
                + COLUMN_PACIENTE_ESTATURA + " REAL, "
                + COLUMN_PACIENTE_NUMERO_HISTORIA + " TEXT UNIQUE"
                + ");";

        String CREATE_TABLE_CONSULTAS = "CREATE TABLE " + TABLE_CONSULTAS + " ("
                + COLUMN_CONSULTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CONSULTA_FECHA + " TEXT, "
                + COLUMN_CONSULTA_MOTIVO + " TEXT, "
                + COLUMN_CONSULTA_MEDICO + " TEXT, "
                + COLUMN_CONSULTA_PACIENTE_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_CONSULTA_PACIENTE_ID + ") REFERENCES "
                + TABLE_PACIENTES + "(" + COLUMN_PACIENTE_ID + ") ON DELETE CASCADE"
                + ");";

        db.execSQL(CREATE_TABLE_PACIENTES);
        db.execSQL(CREATE_TABLE_CONSULTAS);

        insertarDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSULTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACIENTES);
        onCreate(db);
    }

    private void insertarDatosIniciales(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACIENTE_NOMBRE, "Juan");
        values.put(COLUMN_PACIENTE_APELLIDO, "Pérez");
        values.put(COLUMN_PACIENTE_TIPO_IDENTIDAD, "CC");
        values.put(COLUMN_PACIENTE_DNI, "99887766D");
        values.put(COLUMN_PACIENTE_EDAD, 55);
        values.put(COLUMN_PACIENTE_NACIONALIDAD, "Colombia");
        values.put(COLUMN_PACIENTE_ESTATURA, 1.75);
        values.put(COLUMN_PACIENTE_NUMERO_HISTORIA, "HC-2024-001");
        db.insert(TABLE_PACIENTES, null, values);
    }

    public void verificarYInsertarDatosIniciales() {
        SQLiteDatabase db = getWritableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PACIENTES, null)) {
            if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
                insertarDatosIniciales(db);
            }
        }
    }
    // Guardar una consulta en SQLite
    public long insertarConsulta(
            String fecha,
            String motivo,
            String medico,
            long pacienteId
    ) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONSULTA_FECHA, fecha);
        values.put(COLUMN_CONSULTA_MOTIVO, motivo);
        values.put(COLUMN_CONSULTA_MEDICO, medico);
        values.put(COLUMN_CONSULTA_PACIENTE_ID, pacienteId);

        return db.insert(TABLE_CONSULTAS, null, values);
    }

    // Obtener las consultas registradas
    public Cursor obtenerConsultas() {
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT c.id, c.fecha, c.motivo, c.medico, " +
                        "p.nombre || ' ' || p.apellido AS paciente " +
                        "FROM " + TABLE_CONSULTAS + " c " +
                        "INNER JOIN " + TABLE_PACIENTES + " p " +
                        "ON c.paciente_id = p.id " +
                        "ORDER BY c.id DESC",
                null
        );
    }

    // Contar las consultas guardadas
    public int contarConsultas() {
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_CONSULTAS,
                null
        )) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }

        return 0;
    }
}
