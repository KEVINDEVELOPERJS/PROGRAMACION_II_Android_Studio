package com.example.appgestionnotas.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase auxiliar para gestionar la base de datos SQLite de la aplicación
 * Extiende SQLiteOpenHelper para manejar la creación y actualización de la base de datos
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Nombre del archivo de la base de datos
    private static final String DATABASE_NAME = "gestion_notas.db";
    // Versión actual de la base de datos
    private static final int DATABASE_VERSION = 5;

    /**
     * Constructor que inicializa el helper de la base de datos
     * @param context Contexto de la aplicación necesario para crear la base de datos
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Método llamado cuando la base de datos se crea por primera vez
     * Crea las tablas necesarias para la aplicación
     * @param db Instancia de la base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla estudiantes
        db.execSQL("CREATE TABLE estudiantes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "codigo TEXT NOT NULL)");

        // Crear tabla notas
        db.execSQL("CREATE TABLE notas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "estudiante_id INTEGER NOT NULL," +
                "valor REAL NOT NULL," +
                "fecha INTEGER," +
                "materia TEXT," +
                "FOREIGN KEY(estudiante_id) REFERENCES estudiantes(id))");
    }

    /**
     * Método llamado cuando se necesita actualizar la base de datos a una nueva versión
     * Maneja las migraciones de datos entre versiones
     * @param db Instancia de la base de datos
     * @param oldVersion Versión anterior de la base de datos
     * @param newVersion Nueva versión de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Agregar columnas nuevas a la tabla estudiantes
            db.execSQL("ALTER TABLE estudiantes ADD COLUMN promedio REAL DEFAULT 0.0");
        }
        if (oldVersion < 3) {
            // Agregar columna fecha a la tabla notas
            db.execSQL("ALTER TABLE notas ADD COLUMN fecha INTEGER");
        }
        if (oldVersion < 4) {
            // Eliminar columna materia de la tabla estudiantes
            db.execSQL("CREATE TABLE estudiantes_temp (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "codigo TEXT NOT NULL," +
                    "promedio REAL DEFAULT 0.0)");
            
            db.execSQL("INSERT INTO estudiantes_temp (id, nombre, codigo, promedio) " +
                    "SELECT id, nombre, codigo, promedio FROM estudiantes");
            
            db.execSQL("DROP TABLE estudiantes");
            db.execSQL("ALTER TABLE estudiantes_temp RENAME TO estudiantes");
            
            // Agregar columna materia a la tabla notas
            db.execSQL("ALTER TABLE notas ADD COLUMN materia TEXT");
        }
        if (oldVersion < 5) {
            // Eliminar columna promedio de la tabla estudiantes
            db.execSQL("CREATE TABLE estudiantes_temp (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "codigo TEXT NOT NULL)");
            
            db.execSQL("INSERT INTO estudiantes_temp (id, nombre, codigo) " +
                    "SELECT id, nombre, codigo FROM estudiantes");
            
            db.execSQL("DROP TABLE estudiantes");
            db.execSQL("ALTER TABLE estudiantes_temp RENAME TO estudiantes");
        }
    }
}

