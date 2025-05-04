package com.KevinDevJs.SoftOfManagement.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Producto> productos = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private Context context;

    public Inventario() {
        // Constructor sin contexto para compatibilidad
    }

    public Inventario(Context context) {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
        cargarDatos();
    }

    private void cargarDatos() {
        if (dbHelper != null) {
            productos = dbHelper.getAllProductos();
            categorias = dbHelper.getAllCategorias();
        }
    }

    // Métodos para gestionar productos
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        if (dbHelper != null) {
            dbHelper.insertarProducto(producto);
        }
    }

    public boolean eliminarProducto(int id) {
        boolean resultado = productos.removeIf(p -> p.getId() == id);
        if (resultado && dbHelper != null) {
            dbHelper.eliminarProducto(id);
        }
        return resultado;
    }

    public boolean actualizarProducto(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == producto.getId()) {
                productos.set(i, producto);
                if (dbHelper != null) {
                    dbHelper.actualizarProducto(producto);
                }
                return true;
            }
        }
        return false;
    }

    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productos);
    }

    public Producto obtenerProducto(int id) {
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    public List<Producto> obtenerProductosPorCategoria(int categoriaId) {
        List<Producto> result = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCategoriaId() == categoriaId) {
                result.add(producto);
            }
        }
        return result;
    }

    // Métodos para gestionar categorías
    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
        if (dbHelper != null) {
            dbHelper.insertarCategoria(categoria);
        }
    }

    public boolean eliminarCategoria(int id) {
        boolean resultado = categorias.removeIf(c -> c.getId() == id);
        if (resultado && dbHelper != null) {
            dbHelper.eliminarCategoria(id);
        }
        return resultado;
    }

    public boolean actualizarCategoria(Categoria categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId() == categoria.getId()) {
                categorias.set(i, categoria);
                if (dbHelper != null) {
                    dbHelper.actualizarCategoria(categoria);
                }
                return true;
            }
        }
        return false;
    }

    public List<Categoria> obtenerCategorias() {
        return new ArrayList<>(categorias);
    }

    public Categoria obtenerCategoria(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null;
    }
    
    public int generarNuevoIdProducto() {
        if (dbHelper != null) {
            return dbHelper.getMaxProductoId() + 1;
        } else {
            int maxId = 0;
            for (Producto producto : productos) {
                if (producto.getId() > maxId) {
                    maxId = producto.getId();
                }
            }
            return maxId + 1;
        }
    }
    
    public int generarNuevoIdCategoria() {
        if (dbHelper != null) {
            return dbHelper.getMaxCategoriaId() + 1;
        } else {
            int maxId = 0;
            for (Categoria categoria : categorias) {
                if (categoria.getId() > maxId) {
                    maxId = categoria.getId();
                }
            }
            return maxId + 1;
        }
    }
    
    public void setContext(Context context) {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
        cargarDatos();
    }
} 