// Paquete que contiene las clases de acceso a datos
package com.KevDevJs.consultassqlite.dao;

// Importaciones necesarias para el manejo de la base de datos
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.KevDevJs.consultassqlite.db.MiBaseDeDatos;
import com.KevDevJs.consultassqlite.model.Usuario;
import java.util.ArrayList;
import java.util.List;

// Clase que maneja las operaciones de base de datos para usuarios
public class UsuarioDAO {
    // Instancia de la clase helper para la base de datos
    private MiBaseDeDatos dbHelper;

    // Constructor que inicializa el helper de la base de datos
    public UsuarioDAO(Context context) {
        dbHelper = new MiBaseDeDatos(context);
    }

    // Método para insertar un nuevo usuario en la base de datos
    public long insertarUsuario(Usuario usuario) {
        // Obtiene una instancia de la base de datos en modo escritura
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Crea un objeto para almacenar los valores a insertar
        ContentValues valores = new ContentValues();
        // Agrega el nombre del usuario
        valores.put("nombre", usuario.getNombre());
        // Agrega la edad del usuario
        valores.put("edad", usuario.getEdad());
        // Inserta el usuario y obtiene el ID generado
        long id = db.insert("usuarios", null, valores);
        // Cierra la conexión a la base de datos
        db.close();
        return id;
    }

    // Método para obtener todos los usuarios de la base de datos
    public List<Usuario> obtenerUsuarios() {
        // Lista para almacenar los usuarios recuperados
        List<Usuario> usuarios = new ArrayList<>();
        // Obtiene una instancia de la base de datos en modo lectura
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Ejecuta la consulta SQL para obtener todos los usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios", null);

        // Itera sobre los resultados de la consulta
        while (cursor.moveToNext()) {
            // Crea un nuevo objeto Usuario con los datos del cursor
            Usuario usuario = new Usuario(
                cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
            );
            // Establece el ID del usuario
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            // Agrega el usuario a la lista
            usuarios.add(usuario);
        }

        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
        return usuarios;
    }
} 