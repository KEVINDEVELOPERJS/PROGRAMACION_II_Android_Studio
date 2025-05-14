package com.example.appgestionnotas.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appgestionnotas.R;
import com.example.appgestionnotas.controller.EstudianteController;
import com.example.appgestionnotas.controller.NotaController;
import com.example.appgestionnotas.model.Estudiante;
import com.example.appgestionnotas.model.Nota;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Actividad que muestra los detalles de un estudiante y sus notas
 * Permite ver, agregar, editar y eliminar notas del estudiante
 */
public class DetalleEstudianteActivity extends AppCompatActivity {
    // Texto que muestra el nombre del estudiante
    private TextView tvNombre;
    // Texto que muestra el código del estudiante
    private TextView tvCodigo;
    // Texto que se muestra cuando no hay notas
    private TextView textViewEmpty;
    // Botón para agregar una nueva materia con nota
    private MaterialButton btnAgregarMateria;
    // Lista que muestra las notas del estudiante
    private RecyclerView recyclerViewNotas;
    // Controlador que maneja las operaciones con estudiantes
    private EstudianteController estudianteController;
    // Controlador que maneja las operaciones con notas
    private NotaController notaController;
    // ID del estudiante que se está visualizando
    private int estudianteId;
    // Lista de notas del estudiante
    private List<Nota> listaNotas;
    // Adaptador para mostrar las notas en el RecyclerView
    private NotaAdapter adapter;

    /**
     * Método llamado cuando la actividad se crea
     * Inicializa los componentes y carga los datos del estudiante
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamada al método de la clase padre para inicializar la actividad
        super.onCreate(savedInstanceState);
        // Establece el layout que se utilizará para esta actividad
        setContentView(R.layout.activity_detalle_estudiante);

        // Inicializa los controladores con el contexto actual
        estudianteController = new EstudianteController(this);
        notaController = new NotaController(this);

        // Obtiene el ID del estudiante desde la actividad anterior
        estudianteId = getIntent().getIntExtra("ESTUDIANTE_ID", -1);
        // Verifica si se obtuvo un ID válido
        if (estudianteId == -1) {
            // Muestra un mensaje de error si no se pudo obtener el ID
            Toast.makeText(this, "Error al obtener estudiante", Toast.LENGTH_SHORT).show();
            // Finaliza la actividad y vuelve a la anterior
            finish();
            return;
        }

        // Inicializa los componentes de la interfaz de usuario
        tvNombre = findViewById(R.id.tvNombre);
        tvCodigo = findViewById(R.id.tvCodigo);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        btnAgregarMateria = findViewById(R.id.btnAgregarMateria);
        recyclerViewNotas = findViewById(R.id.rvNotas);

        // Configura el RecyclerView para mostrar las notas
        configurarRecyclerView();

        // Carga la información del estudiante en la interfaz
        cargarInformacionEstudiante();

        // Configura el evento de clic para el botón de agregar materia
        btnAgregarMateria.setOnClickListener(v -> mostrarDialogoAgregarMateria());

        // Carga las notas del estudiante en la lista
        actualizarListaNotas();
    }

    /**
     * Muestra un diálogo para agregar una nueva materia con nota
     */
    private void mostrarDialogoAgregarMateria() {
        // Crea un constructor para el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Obtiene el inflador de layouts
        LayoutInflater inflater = getLayoutInflater();
        // Infla el layout del diálogo
        View dialogView = inflater.inflate(R.layout.dialog_agregar_materia, null);
        
        // Obtiene las referencias a los campos del diálogo
        EditText etMateria = dialogView.findViewById(R.id.etMateria);
        EditText etNota = dialogView.findViewById(R.id.etNota);
        
        // Configura el diálogo con su vista, título y botones
        builder.setView(dialogView)
                .setTitle("Agregar Materia con Nota")
                .setPositiveButton("Guardar", (dialog, which) -> {
                    // Obtiene el texto ingresado en los campos y elimina espacios en blanco
                    String materia = etMateria.getText().toString().trim();
                    String notaTexto = etNota.getText().toString().trim();
                    
                    // Verifica que los campos no estén vacíos
                    if (materia.isEmpty() || notaTexto.isEmpty()) {
                        // Muestra un mensaje de error si algún campo está vacío
                        Toast.makeText(DetalleEstudianteActivity.this,
                                "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    try {
                        // Convierte el texto de la nota a un número decimal
                        double valor = Double.parseDouble(notaTexto);
                        // Verifica que la nota esté en el rango válido (0-10)
                        if (valor < 0 || valor > 10) {
                            // Muestra un mensaje de error si la nota está fuera de rango
                            Toast.makeText(DetalleEstudianteActivity.this,
                                    "La nota debe estar entre 0 y 10", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        
                        // Intenta agregar la nota a la base de datos
                        boolean exito = notaController.agregarNotaConMateria(estudianteId, materia, valor);
                        if (exito) {
                            // Actualiza la lista de notas si se agregó correctamente
                            actualizarListaNotas();
                            // Muestra un mensaje de éxito
                            Toast.makeText(DetalleEstudianteActivity.this,
                                    "Materia y nota agregadas correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // Muestra un mensaje de error si no se pudo agregar
                            Toast.makeText(DetalleEstudianteActivity.this,
                                    "Error al agregar la materia y nota", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Muestra un mensaje de error si el texto no es un número válido
                        Toast.makeText(DetalleEstudianteActivity.this,
                                "Ingrese un valor numérico válido para la nota", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null);
        
        // Muestra el diálogo en pantalla
        builder.create().show();
    }

    /**
     * Carga la información del estudiante en la interfaz
     */
    private void cargarInformacionEstudiante() {
        // Obtiene los datos del estudiante desde la base de datos
        Estudiante estudiante = estudianteController.obtenerEstudiantePorId(estudianteId);
        // Si se encontró el estudiante, muestra sus datos en la interfaz
        if (estudiante != null) {
            tvNombre.setText(estudiante.getNombre());
            tvCodigo.setText(estudiante.getCodigo());
        }
    }

    /**
     * Método llamado cuando la actividad vuelve a ser visible
     * Actualiza la información del estudiante
     */
    @Override
    protected void onResume() {
        // Llamada al método de la clase padre
        super.onResume();
        // Carga la información del estudiante
        cargarInformacionEstudiante();
    }

    /**
     * Configura el RecyclerView para mostrar las notas del estudiante
     */
    private void configurarRecyclerView() {
        // Establece el layout manager para el RecyclerView
        recyclerViewNotas.setLayoutManager(new LinearLayoutManager(this));
        // Obtiene la lista de notas del estudiante
        listaNotas = notaController.obtenerNotasPorEstudiante(estudianteId);
        // Crea el adaptador para mostrar las notas
        adapter = new NotaAdapter(listaNotas, notaController, new NotaAdapter.OnNotaActionListener() {
            @Override
            public void onNotaActualizada() {
                // Actualiza la lista de notas cuando se realiza una acción
                actualizarListaNotas();
            }
        });
        // Establece el adaptador en el RecyclerView
        recyclerViewNotas.setAdapter(adapter);
    }

    /**
     * Actualiza la lista de notas del estudiante
     */
    private void actualizarListaNotas() {
        // Obtiene la lista actualizada de notas
        List<Nota> nuevasNotas = notaController.obtenerNotasPorEstudiante(estudianteId);
        // Actualiza los datos en el adaptador
        adapter.actualizarNotas(nuevasNotas);
        
        // Actualiza la visibilidad de los componentes según si hay notas o no
        if (nuevasNotas.isEmpty()) {
            // Si no hay notas, muestra el mensaje de "no hay datos" y oculta la lista
            textViewEmpty.setVisibility(View.VISIBLE);
            recyclerViewNotas.setVisibility(View.GONE);
        } else {
            // Si hay notas, oculta el mensaje de "no hay datos" y muestra la lista
            textViewEmpty.setVisibility(View.GONE);
            recyclerViewNotas.setVisibility(View.VISIBLE);
        }
    }
}
