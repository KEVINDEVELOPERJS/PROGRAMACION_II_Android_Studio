// Paquete que contiene las clases de modelo
package com.KevDevJs.consultassqlite.model;

// Clase que representa un usuario en la aplicación
public class Usuario {
    // Identificador único del usuario
    private int id;
    // Nombre del usuario
    private String nombre;
    // Edad del usuario
    private int edad;

    // Constructor que inicializa un usuario con nombre y edad
    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    // Método getter para obtener el ID del usuario
    public int getId() {
        return id;
    }

    // Método setter para establecer el ID del usuario
    public void setId(int id) {
        this.id = id;
    }

    // Método getter para obtener el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // Método setter para establecer el nombre del usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método getter para obtener la edad del usuario
    public int getEdad() {
        return edad;
    }

    // Método setter para establecer la edad del usuario
    public void setEdad(int edad) {
        this.edad = edad;
    }
} 