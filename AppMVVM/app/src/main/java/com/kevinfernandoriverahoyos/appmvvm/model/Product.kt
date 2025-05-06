package com.kevinfernandoriverahoyos.appmvvm.model

// Definición de la clase de datos Product que representa un producto en la aplicación
// Esta clase es inmutable (todos los campos son val) y se usa para almacenar información de productos
data class Product(
    // Nombre del producto, es un String que identifica el producto
    val name: String,
    
    // Precio del producto, es un Double que representa el valor monetario
    val price: Double,
    
    // ID del recurso de imagen del producto, es un Int que referencia a un recurso drawable
    val imageResId: Int
)