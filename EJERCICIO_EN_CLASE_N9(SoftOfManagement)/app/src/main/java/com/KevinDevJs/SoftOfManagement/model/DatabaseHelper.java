package com.KevinDevJs.SoftOfManagement.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventario.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla Productos
    private static final String TABLE_PRODUCTOS = "productos";
    private static final String COLUMN_PRODUCTO_ID = "id";
    private static final String COLUMN_PRODUCTO_NOMBRE = "nombre";
    private static final String COLUMN_PRODUCTO_DESCRIPCION = "descripcion";
    private static final String COLUMN_PRODUCTO_PRECIO = "precio";
    private static final String COLUMN_PRODUCTO_STOCK = "stock";
    private static final String COLUMN_PRODUCTO_CATEGORIA_ID = "categoria_id";
    private static final String COLUMN_PRODUCTO_IMAGEN_URL = "imagen_url";

    // Tabla Categorías
    private static final String TABLE_CATEGORIAS = "categorias";
    private static final String COLUMN_CATEGORIA_ID = "id";
    private static final String COLUMN_CATEGORIA_NOMBRE = "nombre";
    private static final String COLUMN_CATEGORIA_DESCRIPCION = "descripcion";

    // Instancia singleton
    private static DatabaseHelper instance;

    // Constructor privado
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método para obtener la instancia singleton
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de categorías
        String createCategoriasTable = "CREATE TABLE " + TABLE_CATEGORIAS + "("
                + COLUMN_CATEGORIA_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CATEGORIA_NOMBRE + " TEXT,"
                + COLUMN_CATEGORIA_DESCRIPCION + " TEXT"
                + ")";
        db.execSQL(createCategoriasTable);

        // Crear tabla de productos
        String createProductosTable = "CREATE TABLE " + TABLE_PRODUCTOS + "("
                + COLUMN_PRODUCTO_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRODUCTO_NOMBRE + " TEXT,"
                + COLUMN_PRODUCTO_DESCRIPCION + " TEXT,"
                + COLUMN_PRODUCTO_PRECIO + " REAL,"
                + COLUMN_PRODUCTO_STOCK + " INTEGER,"
                + COLUMN_PRODUCTO_CATEGORIA_ID + " INTEGER,"
                + COLUMN_PRODUCTO_IMAGEN_URL + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_PRODUCTO_CATEGORIA_ID + ") REFERENCES "
                + TABLE_CATEGORIAS + "(" + COLUMN_CATEGORIA_ID + ")"
                + ")";
        db.execSQL(createProductosTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas antiguas si existen
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIAS);
        
        // Crear nuevas tablas
        onCreate(db);
    }

    // Métodos para Productos
    
    public long insertarProducto(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTO_ID, producto.getId());
        values.put(COLUMN_PRODUCTO_NOMBRE, producto.getNombre());
        values.put(COLUMN_PRODUCTO_DESCRIPCION, producto.getDescripcion());
        values.put(COLUMN_PRODUCTO_PRECIO, producto.getPrecio());
        values.put(COLUMN_PRODUCTO_STOCK, producto.getStock());
        values.put(COLUMN_PRODUCTO_CATEGORIA_ID, producto.getCategoriaId());
        values.put(COLUMN_PRODUCTO_IMAGEN_URL, producto.getImagenUrl());
        
        long id = db.insert(TABLE_PRODUCTOS, null, values);
        db.close();
        return id;
    }
    
    public List<Producto> getAllProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_NOMBRE));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_DESCRIPCION));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_PRECIO));
                int stock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_STOCK));
                int categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_CATEGORIA_ID));
                String imagenUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_IMAGEN_URL));
                
                Producto producto = new Producto(id, nombre, descripcion, precio, stock, categoriaId, imagenUrl);
                listaProductos.add(producto);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return listaProductos;
    }
    
    public int actualizarProducto(Producto producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTO_NOMBRE, producto.getNombre());
        values.put(COLUMN_PRODUCTO_DESCRIPCION, producto.getDescripcion());
        values.put(COLUMN_PRODUCTO_PRECIO, producto.getPrecio());
        values.put(COLUMN_PRODUCTO_STOCK, producto.getStock());
        values.put(COLUMN_PRODUCTO_CATEGORIA_ID, producto.getCategoriaId());
        values.put(COLUMN_PRODUCTO_IMAGEN_URL, producto.getImagenUrl());
        
        // Actualizar fila
        int result = db.update(TABLE_PRODUCTOS, values, 
                COLUMN_PRODUCTO_ID + " = ?",
                new String[]{String.valueOf(producto.getId())});
        db.close();
        return result;
    }
    
    public void eliminarProducto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTOS, COLUMN_PRODUCTO_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    
    // Métodos para Categorías
    
    public long insertarCategoria(Categoria categoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORIA_ID, categoria.getId());
        values.put(COLUMN_CATEGORIA_NOMBRE, categoria.getNombre());
        values.put(COLUMN_CATEGORIA_DESCRIPCION, categoria.getDescripcion());
        
        long id = db.insert(TABLE_CATEGORIAS, null, values);
        db.close();
        return id;
    }
    
    public List<Categoria> getAllCategorias() {
        List<Categoria> listaCategorias = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIAS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA_NOMBRE));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA_DESCRIPCION));
                
                Categoria categoria = new Categoria(id, nombre, descripcion);
                listaCategorias.add(categoria);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return listaCategorias;
    }
    
    public int actualizarCategoria(Categoria categoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORIA_NOMBRE, categoria.getNombre());
        values.put(COLUMN_CATEGORIA_DESCRIPCION, categoria.getDescripcion());
        
        // Actualizar fila
        int result = db.update(TABLE_CATEGORIAS, values, 
                COLUMN_CATEGORIA_ID + " = ?",
                new String[]{String.valueOf(categoria.getId())});
        db.close();
        return result;
    }
    
    public void eliminarCategoria(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIAS, COLUMN_CATEGORIA_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    
    public int getMaxProductoId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + COLUMN_PRODUCTO_ID + ") FROM " + TABLE_PRODUCTOS;
        Cursor cursor = db.rawQuery(query, null);
        int maxId = 0;
        
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        
        cursor.close();
        db.close();
        return maxId;
    }
    
    public int getMaxCategoriaId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + COLUMN_CATEGORIA_ID + ") FROM " + TABLE_CATEGORIAS;
        Cursor cursor = db.rawQuery(query, null);
        int maxId = 0;
        
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        
        cursor.close();
        db.close();
        return maxId;
    }
} 