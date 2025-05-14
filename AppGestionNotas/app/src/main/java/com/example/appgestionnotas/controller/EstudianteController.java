package com.example.appgestionnotas.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.appgestionnotas.model.DatabaseHelper;
import com.example.appgestionnotas.model.Estudiante;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los estudiantes en la base de datos
 * Esta clase maneja todas las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para los estudiantes
 */
public class EstudianteController {
    // Instancia del helper de base de datos para realizar operaciones
    private final DatabaseHelper dbHelper;

    /**
     * Constructor que inicializa el controlador con el contexto de la aplicación
     * @param context Contexto de la aplicación necesario para acceder a la base de datos
     */
    public EstudianteController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Agrega un nuevo estudiante a la base de datos
     * @param nombre Nombre del estudiante
     * @param codigo Código único del estudiante
     */
    public void agregarEstudiante(String nombre, String codigo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("codigo", codigo);
        db.insert("estudiantes", null, values);
        db.close();
    }

    /**
     * Obtiene la lista de todos los estudiantes registrados
     * @return List<Estudiante> Lista de todos los estudiantes
     */
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM estudiantes", null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(new Estudiante(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    /**
     * Obtiene un estudiante específico por su ID
     * @param id ID del estudiante a buscar
     * @return Estudiante Objeto estudiante con toda su información
     */
    public Estudiante obtenerEstudiantePorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM estudiantes WHERE id = ?", new String[] { String.valueOf(id) });

        Estudiante estudiante = null;
        if (cursor.moveToFirst()) {
            estudiante = new Estudiante(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            );
        }
        cursor.close();
        return estudiante;
    }

    /**
     * Elimina un estudiante y todas sus notas asociadas
     * @param id ID del estudiante a eliminar
     * @return boolean Indica si la eliminación fue exitosa
     */
    public boolean eliminarEstudiante(int id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Primero eliminamos las notas asociadas al estudiante
            db.delete("notas", "estudiante_id = ?", new String[] { String.valueOf(id) });
            // Luego eliminamos al estudiante
            int resultado = db.delete("estudiantes", "id = ?", new String[] { String.valueOf(id) });
            db.close();
            return resultado > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza la información de un estudiante existente
     * @param id ID del estudiante a actualizar
     * @param nombre Nuevo nombre del estudiante
     * @param codigo Nuevo código del estudiante
     */
    public void actualizarEstudiante(int id, String nombre, String codigo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("codigo", codigo);
        db.update("estudiantes", values, "id = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}

