package com.example.appgestionnotas.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.appgestionnotas.model.DatabaseHelper;
import com.example.appgestionnotas.model.Nota;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar las operaciones relacionadas con las notas en la base de datos
 * Esta clase maneja todas las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para las notas
 */
public class NotaController {
    // Instancia del helper de base de datos para realizar operaciones
    private final DatabaseHelper dbHelper;

    /**
     * Constructor que inicializa el controlador con el contexto de la aplicación
     * @param context Contexto de la aplicación necesario para acceder a la base de datos
     */
    public NotaController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Agrega una nueva nota para un estudiante específico
     * @param estudianteId ID del estudiante al que se le asigna la nota
     * @param valor Valor numérico de la nota
     */
    public void agregarNota(int estudianteId, double valor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estudiante_id", estudianteId);
        values.put("valor", valor);
        values.put("fecha", System.currentTimeMillis());
        db.insert("notas", null, values);
        db.close();
    }

    /**
     * Agrega una nueva nota con materia específica para un estudiante
     * @param estudianteId ID del estudiante
     * @param materia Nombre de la materia
     * @param valor Valor numérico de la nota
     * @return boolean Indica si la operación fue exitosa
     */
    public boolean agregarNotaConMateria(int estudianteId, String materia, double valor) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("estudiante_id", estudianteId);
            values.put("materia", materia);
            values.put("valor", valor);
            values.put("fecha", System.currentTimeMillis());
            long resultado = db.insert("notas", null, values);
            db.close();
            return resultado != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las notas de un estudiante específico
     * @param estudianteId ID del estudiante
     * @return List<Nota> Lista de notas del estudiante ordenadas por fecha
     */
    public List<Nota> obtenerNotasPorEstudiante(int estudianteId) {
        List<Nota> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notas WHERE estudiante_id = ? ORDER BY fecha DESC",
                new String[] { String.valueOf(estudianteId) });

        if (cursor.moveToFirst()) {
            do {
                Nota nota = new Nota(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
                );
                
                // Establecer la fecha
                if (!cursor.isNull(cursor.getColumnIndexOrThrow("fecha"))) {
                    Date fecha = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("fecha")));
                    nota.setFecha(fecha);
                }
                
                // Establecer la materia
                if (!cursor.isNull(cursor.getColumnIndexOrThrow("materia"))) {
                    nota.setMateria(cursor.getString(cursor.getColumnIndexOrThrow("materia")));
                }
                
                lista.add(nota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    /**
     * Calcula el promedio de todas las notas de un estudiante
     * @param estudianteId ID del estudiante
     * @return double Promedio de las notas
     */
    public double calcularPromedio(int estudianteId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(valor) FROM notas WHERE estudiante_id = ?",
                new String[] { String.valueOf(estudianteId) });

        double promedio = 0;
        if (cursor.moveToFirst()) {
            promedio = cursor.getDouble(0);
        }
        cursor.close();
        return promedio;
    }
    
    /**
     * Obtiene los promedios por materia de un estudiante
     * @param estudianteId ID del estudiante
     * @return Map<String, Double> Mapa con las materias y sus respectivos promedios
     */
    public Map<String, Double> obtenerPromediosPorMateria(int estudianteId) {
        Map<String, Double> promediosPorMateria = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        // Consulta para obtener el promedio de cada materia
        Cursor cursor = db.rawQuery(
            "SELECT materia, AVG(valor) as promedio FROM notas " +
            "WHERE estudiante_id = ? AND materia IS NOT NULL AND materia != '' " +
            "GROUP BY materia",
            new String[] { String.valueOf(estudianteId) }
        );
        
        if (cursor.moveToFirst()) {
            do {
                String materia = cursor.getString(0);
                double promedio = cursor.getDouble(1);
                promediosPorMateria.put(materia, promedio);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return promediosPorMateria;
    }
    
    /**
     * Actualiza una nota existente
     * @param notaId ID de la nota a actualizar
     * @param materia Nueva materia de la nota
     * @param valor Nuevo valor de la nota
     * @return boolean Indica si la actualización fue exitosa
     */
    public boolean actualizarNota(int notaId, String materia, double valor) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("materia", materia);
            values.put("valor", valor);
            values.put("fecha", System.currentTimeMillis());
            
            int resultado = db.update("notas", values, "id = ?", 
                    new String[] { String.valueOf(notaId) });
            
            db.close();
            return resultado > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Elimina una nota específica
     * @param notaId ID de la nota a eliminar
     * @return boolean Indica si la eliminación fue exitosa
     */
    public boolean eliminarNota(int notaId) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int resultado = db.delete("notas", "id = ?", 
                    new String[] { String.valueOf(notaId) });
            db.close();
            return resultado > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene una nota específica por su ID
     * @param notaId ID de la nota a obtener
     * @return Nota Objeto nota con toda su información
     */
    public Nota obtenerNotaPorId(int notaId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notas WHERE id = ?", 
                new String[] { String.valueOf(notaId) });

        Nota nota = null;
        if (cursor.moveToFirst()) {
            nota = new Nota(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            );
            
            // Establecer la fecha
            if (!cursor.isNull(cursor.getColumnIndexOrThrow("fecha"))) {
                Date fecha = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("fecha")));
                nota.setFecha(fecha);
            }
            
            // Establecer la materia
            if (!cursor.isNull(cursor.getColumnIndexOrThrow("materia"))) {
                nota.setMateria(cursor.getString(cursor.getColumnIndexOrThrow("materia")));
            }
        }
        cursor.close();
        db.close();
        return nota;
    }
}
