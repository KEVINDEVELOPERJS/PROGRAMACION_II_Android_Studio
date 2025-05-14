package com.example.appgestionnotas.view;

// Importaciones necesarias para la funcionalidad de la actividad
import android.content.Intent;  // Para iniciar nuevas actividades
import android.os.Bundle;       // Para manejar el estado de la actividad
import android.view.View;       // Para trabajar con elementos visuales
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;  // Clase base para la actividad
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appgestionnotas.R;  // Recursos de la aplicación
import com.example.appgestionnotas.controller.EstudianteController;  // Controlador para la lógica de estudiantes
import com.example.appgestionnotas.model.Estudiante;  // Modelo de datos de estudiante
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.List;  // Para almacenar la colección de estudiantes

/**
 * Actividad principal que muestra la lista de estudiantes y permite navegar a otras pantallas
 */
public class MainActivity extends AppCompatActivity implements EstudianteAdapter.OnEstudianteClickListener {
    // Variables miembro para mantener referencias a elementos importantes
    private RecyclerView recyclerViewEstudiantes;
    private TextView textViewEmpty;
    private EstudianteController controller;
    private EstudianteAdapter adapter;
    private List<Estudiante> listaEstudiantes;

    /**
     * Método llamado cuando la actividad se crea por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // Llama al método de la clase padre
        setContentView(R.layout.activity_main);  // Establece el layout XML para esta actividad

        // Inicializa el controlador pasando el contexto de la actividad actual
        controller = new EstudianteController(this);

        // Obtiene referencias a los elementos de la interfaz definidos en el XML
        recyclerViewEstudiantes = findViewById(R.id.recyclerViewEstudiantes);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        ExtendedFloatingActionButton btnAgregarEstudiante = findViewById(R.id.btnAgregarEstudiante);

        recyclerViewEstudiantes.setLayoutManager(new LinearLayoutManager(this));
        listaEstudiantes = controller.obtenerEstudiantes();
        adapter = new EstudianteAdapter(listaEstudiantes, this);
        recyclerViewEstudiantes.setAdapter(adapter);

        // Configura el evento de clic para el botón de agregar estudiante
        btnAgregarEstudiante.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarEstudianteActivity.class);
            startActivity(intent);
        });

        actualizarVistaEstudiantes();
    }

    /**
     * Método llamado cuando la actividad vuelve a ser visible
     * (por ejemplo, al regresar de otra actividad)
     */
    @Override
    protected void onResume() {
        super.onResume();  // Llama al método de la clase padre
        actualizarVistaEstudiantes();
    }

    private void actualizarVistaEstudiantes() {
        listaEstudiantes = controller.obtenerEstudiantes();
        adapter.actualizarEstudiantes(listaEstudiantes);
        
        if (listaEstudiantes.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            recyclerViewEstudiantes.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            recyclerViewEstudiantes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEstudianteClick(Estudiante estudiante) {
        Intent intent = new Intent(this, DetalleEstudianteActivity.class);
        intent.putExtra("ESTUDIANTE_ID", estudiante.getId());
        startActivity(intent);
    }

    @Override
    public void onEstudianteEdit(Estudiante estudiante) {
        Intent intent = new Intent(this, AgregarEstudianteActivity.class);
        intent.putExtra("ESTUDIANTE_ID", estudiante.getId());
        startActivity(intent);
    }

    @Override
    public void onEstudianteDelete(Estudiante estudiante) {
        if (controller.eliminarEstudiante(estudiante.getId())) {
            Toast.makeText(this, R.string.estudiante_eliminado, Toast.LENGTH_SHORT).show();
            actualizarVistaEstudiantes();
        } else {
            Toast.makeText(this, R.string.error_eliminar, Toast.LENGTH_SHORT).show();
        }
    }
}