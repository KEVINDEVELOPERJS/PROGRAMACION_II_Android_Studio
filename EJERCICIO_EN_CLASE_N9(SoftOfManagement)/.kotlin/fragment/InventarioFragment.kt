package fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.KevinDevJs.SoftOfManagement.databinding.FragmentInventarioBinding

class InventarioFragment : Fragment() {

    private var _binding: FragmentInventarioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actualizarResumen()
    }

    override fun onResume() {
        super.onResume()
        actualizarResumen()
    }

    private fun actualizarResumen() {
        val mainActivity = activity as MainActivity
        val inventario = mainActivity.inventario

        // Obtener datos
        val totalProductos = inventario.obtenerProductos().size
        val totalCategorias = inventario.obtenerCategorias().size
        val valorTotal = inventario.obtenerProductos().sumOf { it.precio * it.stock }
        val stockTotal = inventario.obtenerProductos().sumOf { it.stock }

        // Mostrar estadísticas
        binding.tvTotalProductos.text = "Total de Productos: $totalProductos"
        binding.tvTotalCategorias.text = "Total de Categorías: $totalCategorias"
        binding.tvValorInventario.text =
            "Valor Total del Inventario: $${String.format("%.2f", valorTotal)}"
        binding.tvTotalStock.text = "Total de Unidades en Stock: $stockTotal"

        // Producto con mayor stock
        val productoMayorStock = inventario.obtenerProductos().maxByOrNull { it.stock }
        productoMayorStock?.let {
            binding.tvMayorStock.text =
                "Producto con mayor stock: ${it.nombre} (${it.stock} unidades)"
        } ?: run {
            binding.tvMayorStock.text = "No hay productos disponibles"
        }

        // Producto más valioso
        val productoMasValioso = inventario.obtenerProductos().maxByOrNull { it.precio }
        productoMasValioso?.let {
            binding.tvMasValioso.text = "Producto más valioso: ${it.nombre} ($${it.precio})"
        } ?: run {
            binding.tvMasValioso.text = "No hay productos disponibles"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 