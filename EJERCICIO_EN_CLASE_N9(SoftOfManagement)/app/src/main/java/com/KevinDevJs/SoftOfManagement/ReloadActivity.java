package com.KevinDevJs.SoftOfManagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.KevinDevJs.SoftOfManagement.databinding.ActivityReloadBinding;

/**
 * Actividad de carga inicial que muestra un icono de gestión de productos
 * durante unos segundos antes de pasar a la actividad principal.
 */
public class ReloadActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 2000; // 2 segundos
    private ActivityReloadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReloadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuramos un temporizador para pasar a MainActivity después de 2 segundos
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Este método se ejecutará una vez que el temporizador finalice
                Intent intent = new Intent(ReloadActivity.this, MainActivity.class);
                startActivity(intent);

                // Cerramos esta actividad para que no quede en la pila
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
} 