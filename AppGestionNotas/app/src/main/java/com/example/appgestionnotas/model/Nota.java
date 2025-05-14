package com.example.appgestionnotas.model;

import java.util.Date;

/**
 * Clase que representa una nota en el sistema
 * Contiene la información de una nota como su ID, valor, fecha, materia y el ID del estudiante al que pertenece
 */
public class Nota {
    // Identificador único de la nota
    private final int id;
    // ID del estudiante al que pertenece la nota
    private final int estudianteId;
    // Valor numérico de la nota
    private final double valor;
    // Fecha en que se registró la nota
    private Date fecha;
    // Nombre de la materia a la que corresponde la nota
    private String materia;

    /**
     * Constructor que inicializa una nueva nota
     * @param id Identificador único de la nota
     * @param estudianteId ID del estudiante al que pertenece la nota
     * @param valor Valor numérico de la nota
     */
    public Nota(int id, int estudianteId, double valor) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.valor = valor;
        this.fecha = new Date();
    }

    /**
     * Obtiene el ID de la nota
     * @return int ID de la nota
     */
    public int getId() { return id; }

    /**
     * Obtiene el ID del estudiante al que pertenece la nota
     * @return int ID del estudiante
     */
    public int getEstudianteId() { return estudianteId; }

    /**
     * Obtiene el valor numérico de la nota
     * @return double Valor de la nota
     */
    public double getValor() { return valor; }

    /**
     * Obtiene la fecha de registro de la nota
     * @return Date Fecha de la nota
     */
    public Date getFecha() { return fecha; }

    /**
     * Establece la fecha de la nota
     * @param fecha Nueva fecha para la nota
     */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /**
     * Obtiene la materia a la que corresponde la nota
     * @return String Nombre de la materia
     */
    public String getMateria() { return materia; }

    /**
     * Establece la materia de la nota
     * @param materia Nombre de la materia
     */
    public void setMateria(String materia) { this.materia = materia; }

    /**
     * Convierte la nota a una representación en texto
     * @return String Representación de la nota en formato "materia: valor" o "Nota: valor"
     */
    @Override
    public String toString() {
        if (materia != null && !materia.isEmpty()) {
            return String.format("%s: %.2f", materia, valor);
        } else {
            return String.format("Nota: %.2f", valor);
        }
    }
}

