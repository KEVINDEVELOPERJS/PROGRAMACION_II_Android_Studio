package  com.kevinfernandoriverahoyos.appmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kevinfernandoriverahoyos.appmvvm.R
import com.kevinfernandoriverahoyos.appmvvm.model.Product
import kotlin.random.Random

/**
 * Esta clase `ProductViewModel` es un ViewModel responsable de gestionar y
 * proporcionar datos relacionados con un producto, usado en una arquitectura MVVM.
 */
class ProductViewModel : ViewModel() {
    // MutableLiveData para almacenar los datos del producto actual. Es mutable, por lo que se puede actualizar.
    // Está marcado como privado porque solo se modifica dentro de este ViewModel.
    private val _currentProduct = MutableLiveData<Product>()

    // LiveData expuesta públicamente del producto actual. Es de solo lectura,
    // asegurando que los datos solo se puedan modificar a través del ViewModel.
    val currentProduct: LiveData<Product> = _currentProduct

    // Una lista de productos para elegir, cada uno con un nombre, precio e imagen.
    // Es una lista fija, que actúa como una fuente de datos simulada o estática para este ejemplo.
    private val products = listOf(
        Product(
            "Laptop Gaming", // Nombre del producto
            1299.99, // Precio del producto
            R.drawable.laptopgaming // ID del recurso para la imagen del producto
        ),
        Product(
            "Smartphone Pro",
            899.99,
            R.drawable.smartphone
        ),
        Product(
            "Tablet Premium",
            499.99,
            R.drawable.tabletpremium
        ),
        Product(
            "Smartwatch Elite",
            299.99,
            R.drawable.smartwatchelite
        ),
        Product(
            "Auriculares Wireless",
            199.99,
            R.drawable.auriculareswireless
        ),
        Product(
            "Teclado Mecánico",
            129.99,
            R.drawable.tecladomecanico
        ),
        Product(
            "Mouse Gaming",
            79.99,
            R.drawable.mousegaming
        ),
        Product(
            "Monitor 4K",
            399.99,
            R.drawable.monito4k
        )
    )

    /**
     * Esta función `generateRandomProduct` es responsable de seleccionar un
     * producto aleatorio de la lista y actualizar `_currentProduct`.
     */
    fun generateRandomProduct() {
        // Genera un índice aleatorio dentro de los límites del tamaño de la lista de productos.
        val randomIndex = Random.Default.nextInt(products.size)

        // Establece el valor de _currentProduct con el producto en el índice aleatorio.
        // Esto actualiza el LiveData, y cualquier observador de 'currentProduct' será notificado.
        _currentProduct.value = products[randomIndex]
    }
}

