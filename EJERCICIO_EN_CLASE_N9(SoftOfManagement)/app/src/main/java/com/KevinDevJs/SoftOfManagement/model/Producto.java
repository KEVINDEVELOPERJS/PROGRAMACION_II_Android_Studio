package com.KevinDevJs.SoftOfManagement.model;

import java.util.Objects;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int categoriaId;
    private String imagenUrl;

    public Producto(int id, String nombre, String descripcion, double precio, int stock, int categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = categoriaId;
        this.imagenUrl = null;
    }

    public Producto(int id, String nombre, String descripcion, double precio, int stock, int categoriaId, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = categoriaId;
        this.imagenUrl = imagenUrl;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Producto(id=" + id + ", nombre='" + nombre + "', precio=" + precio + ", stock=" + stock + ")";
    }
} 