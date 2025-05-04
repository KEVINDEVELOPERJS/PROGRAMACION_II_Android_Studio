package com.cristhian.appgestionnotas.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristhian.appgestionnotas.R;
import com.cristhian.appgestionnotas.controller.EstudianteController;
import com.cristhian.appgestionnotas.databinding.ActivityMainBinding;
import com.cristhian.appgestionnotas.view.adapters.EstudianteAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding ;
    private EstudianteController estudianteController;

    // private RecyclerView rvListaEstudiantes;
    // Declaracion del adapter para el RcyclerView
    private EstudianteAdapter estudianteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        estudianteController = new EstudianteController(this);
        estudianteController.agregarEstudiante("Juan", "12345");
        estudianteController.agregarEstudiante("María", "67890");

        // Brindar contexto al recyclerView
        binding.rvListaEstudiantes.setLayoutManager(new LinearLayoutManager(this));

        estudianteAdapter = new EstudianteAdapter(estudianteController.obtenerEstudiantes());
        binding.rvListaEstudiantes.setAdapter(estudianteAdapter);
    }
}