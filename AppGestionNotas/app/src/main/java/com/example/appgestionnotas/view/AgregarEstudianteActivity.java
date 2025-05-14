package com.example.appgestionnotas.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appgestionnotas.R;
import com.example.appgestionnotas.controller.EstudianteController;
import com.example.appgestionnotas.model.Estudiante;

/**
 * Actividad para agregar o editar un estudiante
 * Permite al usuario ingresar el nombre y código de un estudiante
 */
public class AgregarEstudianteActivity extends AppCompatActivity {
    // Campo de texto para ingresar el nombre del estudiante
    private EditText etNombre;
    // Campo de texto para ingresar el código del estudiante
    private EditText etCodigo;
    // Botón para guardar los datos del estudiante
    private Button btnGuardar;
    // Controlador que maneja las operaciones con estudiantes
    private EstudianteController controller;
    // ID del estudiante que se está editando (-1 si es un nuevo estudiante)
    private int estudianteId = -1;

    /**
     * Método llamado cuando la actividad se crea
     * Inicializa los componentes y configura los eventos
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamada al método de la clase padre para inicializar la actividad
        super.onCreate(savedInstanceState);
        // Establece el layout que se utilizará para esta actividad
        setContentView(R.layout.activity_agregar_estudiante);

        // Inicializa el controlador de estudiantes con el contexto actual
        controller = new EstudianteController(this);

        // Inicializa los componentes de la interfaz de usuario
        etNombre = findViewById(R.id.etNombre);
        etCodigo = findViewById(R.id.etCodigo);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Verifica si se está editando un estudiante existente
        estudianteId = getIntent().getIntExtra("ESTUDIANTE_ID", -1);
        // Si el ID no es -1, significa que se está editando un estudiante existente
        if (estudianteId != -1) {
            // Obtiene los datos del estudiante desde la base de datos
            Estudiante estudiante = controller.obtenerEstudiantePorId(estudianteId);
            // Si se encontró el estudiante, muestra sus datos en los campos
            if (estudiante != null) {
                etNombre.setText(estudiante.getNombre());
                etCodigo.setText(estudiante.getCodigo());
                btnGuardar.setText(R.string.guardar);
            }
        }

        // Configura el evento de clic para el botón guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el texto ingresado en los campos y elimina espacios en blanco
                String nombre = etNombre.getText().toString().trim();
                String codigo = etCodigo.getText().toString().trim();

                // Verifica que los campos no estén vacíos
                if (nombre.isEmpty() || codigo.isEmpty()) {
                    // Muestra un mensaje de error si algún campo está vacío
                    Toast.makeText(AgregarEstudianteActivity.this,
                            "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Si es un nuevo estudiante (estudianteId == -1)
                if (estudianteId == -1) {
                    // Agrega el nuevo estudiante a la base de datos
                    controller.agregarEstudiante(nombre, codigo);
                    // Muestra un mensaje de éxito
                    Toast.makeText(AgregarEstudianteActivity.this,
                            "Estudiante agregado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Actualiza los datos del estudiante existente
                    controller.actualizarEstudiante(estudianteId, nombre, codigo);
                    // Muestra un mensaje de éxito
                    Toast.makeText(AgregarEstudianteActivity.this,
                            "Estudiante actualizado correctamente", Toast.LENGTH_SHORT).show();
                }
                // Finaliza la actividad y vuelve a la anterior
                finish();
            }
        });
    }
}