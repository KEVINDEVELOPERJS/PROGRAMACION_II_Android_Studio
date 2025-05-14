// Paquete que contiene las clases relacionadas con la base de datos
package com.KevDevJs.consultassqlite.db;

// Importaciones necesarias para el manejo de SQLite
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Clase que maneja la creación y actualización de la base de datos SQLite
public class MiBaseDeDatos extends SQLiteOpenHelper {
    // Nombre de la base de datos
    private static final String NOMBRE_DB = "MiDB";
    // Versión actual de la base de datos
    private static final int VERSION_DB = 1;

    // Constructor que inicializa la base de datos
    public MiBaseDeDatos(Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    // Método que se ejecuta cuando se crea la base de datos por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL para crear la tabla de usuarios
        String crearTabla = "CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, edad INTEGER)";
        // Ejecuta la creación de la tabla
        db.execSQL(crearTabla);
    }

    // Método que se ejecuta cuando se actualiza la versión de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina la tabla existente si existe
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        // Crea la tabla nuevamente con la nueva estructura
        onCreate(db);
    }
} 