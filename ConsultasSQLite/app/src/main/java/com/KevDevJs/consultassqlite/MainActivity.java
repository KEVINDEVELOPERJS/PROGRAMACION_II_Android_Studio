package com.KevDevJs.consultassqlite;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.KevDevJs.consultassqlite.dao.UsuarioDAO;
import com.KevDevJs.consultassqlite.databinding.ActivityMainBinding;
import com.KevDevJs.consultassqlite.model.Usuario;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usuarioDAO = new UsuarioDAO(this);

        binding.btnGuardar.setOnClickListener(v -> {
            String nombre = binding.etNombre.getText().toString();
            String edadStr = binding.etEdad.getText().toString();

            if (nombre.isEmpty() || edadStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int edad = Integer.parseInt(edadStr);
                Usuario usuario = new Usuario(nombre, edad);
                long id = usuarioDAO.insertarUsuario(usuario);

                if (id != -1) {
                    Toast.makeText(MainActivity.this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();
                    mostrarUsuarios();
                    limpiarCampos();
                } else {
                    Toast.makeText(MainActivity.this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "La edad debe ser un número", Toast.LENGTH_SHORT).show();
            }
        });

        mostrarUsuarios();
    }

    private void mostrarUsuarios() {
        StringBuilder resultado = new StringBuilder();
        for (Usuario usuario : usuarioDAO.obtenerUsuarios()) {
            resultado.append("ID: ").append(usuario.getId())
                    .append(", Nombre: ").append(usuario.getNombre())
                    .append(", Edad: ").append(usuario.getEdad())
                    .append("\n");
        }
        binding.tvResultado.setText(resultado.toString());
    }

    private void limpiarCampos() {
        binding.etNombre.setText("");
        binding.etEdad.setText("");
    }
}