package com.kevinfernandoriverahoyos.appmvvm.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kevinfernandoriverahoyos.appmvvm.databinding.ActivityMainBinding
import com.kevinfernandoriverahoyos.appmvvm.viewmodel.ProductViewModel

/**
 * `MainActivity` es la actividad principal de la aplicación.
 * Se encarga de mostrar la interfaz de usuario y de interactuar con el usuario.
 * Utiliza el patrón MVVM, donde `ProductViewModel` gestiona los datos y la lógica.
 */
class MainActivity : AppCompatActivity() {
    // `binding` se utiliza para acceder a las vistas del layout sin necesidad de `findViewById`.
    private lateinit var binding: ActivityMainBinding

    // `viewModel` es una instancia de `ProductViewModel`. Se crea y gestiona con `viewModels()`.
    // `viewModels()` es una función delegada que proporciona una instancia del ViewModel asociado a esta actividad.
    private val viewModel: ProductViewModel by viewModels()

    /**
     * `onCreate` es el método que se llama cuando se crea la actividad.
     * Se encarga de inicializar la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa el binding para poder acceder a las vistas del layout.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Establece la vista raíz del layout como la vista de la actividad.
        setContentView(binding.root)

        // Configura los observadores de LiveData.
        setupObservers()
        // Configura los listeners de los clicks.
        setupClickListeners()
    }

    /**
     * `setupObservers` configura los observadores de los LiveData del ViewModel.
     * Los observadores se activarán cuando el valor del LiveData cambie.
     */
    private fun setupObservers() {
        // Observa el LiveData `currentProduct` del ViewModel.
        viewModel.currentProduct.observe(this, Observer { product ->
            // Si el producto no es nulo, actualiza la interfaz de usuario con los datos del producto.
            product?.let {
                // Establece el texto del TextView `tvProductName` con el nombre del producto.
                binding.tvProductName.text = it.name
                // Establece el texto del TextView `tvProductPrice` con el precio del producto.
                binding.tvProductPrice.text = "Precio: $${it.price}"
                // Establece la imagen del ImageView `ivProduct` con el ID de recurso de la imagen del producto.
                binding.ivProduct.setImageResource(it.imageResId)
            }
        })
    }

    /**
     * `setupClickListeners` configura los listeners para los clicks en las vistas.
     * Define la acción que se ejecutará cuando el usuario haga click en la vista.
     */
    private fun setupClickListeners() {
        // Establece un listener de click en el contenedor de la vista (`viewContainer`).
        binding.viewContainer.setOnClickListener {
            // Cuando se hace click, se llama al método `generateRandomProduct` del ViewModel.
            viewModel.generateRandomProduct()
        }
    }
}