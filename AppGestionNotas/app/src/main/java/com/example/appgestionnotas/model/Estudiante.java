package com.example.appgestionnotas.model;

import androidx.annotation.NonNull;

/**
 * Clase que representa a un estudiante en el sistema
 * Contiene la información básica de un estudiante como su ID, nombre y código
 */
public class Estudiante {
    // Identificador único del estudiante
    private final int id;
    // Nombre completo del estudiante
    private final String nombre;
    // Código único del estudiante
    private final String codigo;

    /**
     * Constructor que inicializa un nuevo estudiante
     * @param id Identificador único del estudiante
     * @param nombre Nombre completo del estudiante
     * @param codigo Código único del estudiante
     */
    public Estudiante(int id, String nombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    /**
     * Obtiene el ID del estudiante
     * @return int ID del estudiante
     */
    public int getId() { return id; }

    /**
     * Obtiene el nombre del estudiante
     * @return String Nombre del estudiante
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el código del estudiante
     * @return String Código del estudiante
     */
    public String getCodigo() { return codigo; }

    /**
     * Convierte el estudiante a una representación en texto
     * @return String Representación del estudiante en formato "nombre (código)"
     */
    @NonNull
    @Override
    public String toString() {
        return nombre + " (" + codigo + ")";
    }
}