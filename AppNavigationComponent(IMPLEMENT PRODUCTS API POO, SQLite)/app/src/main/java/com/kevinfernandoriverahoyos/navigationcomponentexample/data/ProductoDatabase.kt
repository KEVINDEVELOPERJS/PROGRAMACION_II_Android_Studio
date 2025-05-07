package com.kevinfernandoriverahoyos.navigationcomponentexample.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductoDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "productos.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PRODUCTOS = "productos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CATEGORIA = "categoria"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_IMAGEN_URL = "imagen_url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PRODUCTOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_CATEGORIA TEXT NOT NULL,
                $COLUMN_PRECIO REAL NOT NULL,
                $COLUMN_IMAGEN_URL TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTOS")
        onCreate(db)
    }

    fun insertarProducto(producto: Producto): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, producto.nombre)
            put(COLUMN_CATEGORIA, producto.categoria)
            put(COLUMN_PRECIO, producto.precio)
            put(COLUMN_IMAGEN_URL, producto.imagenUrl)
        }
        return db.insert(TABLE_PRODUCTOS, null, values)
    }

    fun actualizarProducto(producto: Producto): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, producto.nombre)
            put(COLUMN_CATEGORIA, producto.categoria)
            put(COLUMN_PRECIO, producto.precio)
            put(COLUMN_IMAGEN_URL, producto.imagenUrl)
        }
        return db.update(TABLE_PRODUCTOS, values, "$COLUMN_ID = ?", arrayOf(producto.id.toString()))
    }

    fun eliminarProducto(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_PRODUCTOS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun obtenerProductos(): List<Producto> {
        val productos = mutableListOf<Producto>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_PRODUCTOS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NOMBRE ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val nombre = getString(getColumnIndexOrThrow(COLUMN_NOMBRE))
                val categoria = getString(getColumnIndexOrThrow(COLUMN_CATEGORIA))
                val precio = getDouble(getColumnIndexOrThrow(COLUMN_PRECIO))
                val imagenUrl = getString(getColumnIndexOrThrow(COLUMN_IMAGEN_URL))

                productos.add(Producto(id, nombre, categoria, precio, imagenUrl))
            }
        }
        cursor.close()
        return productos
    }

    fun obtenerProducto(id: Int): Producto? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_PRODUCTOS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
            val categoria = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA))
            val precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO))
            val imagenUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_URL))
            cursor.close()
            Producto(id, nombre, categoria, precio, imagenUrl)
        } else {
            cursor.close()
            null
        }
    }
} 