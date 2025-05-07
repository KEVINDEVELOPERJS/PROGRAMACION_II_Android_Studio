package com.kevinfernandoriverahoyos.navigationcomponentexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinfernandoriverahoyos.navigationcomponentexample.adapters.ProductoAdapter
import com.kevinfernandoriverahoyos.navigationcomponentexample.data.Producto
import com.kevinfernandoriverahoyos.navigationcomponentexample.data.ProductoDatabase
import com.kevinfernandoriverahoyos.navigationcomponentexample.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val productos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter
    private lateinit var db: ProductoDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = ProductoDatabase(requireContext())
        setupRecyclerView()
        setupFab()
        cargarProductos()
        setupResultListener()
    }

    private fun setupRecyclerView() {
        try {
            adapter = ProductoAdapter(
                productos = productos,
                onItemClick = { producto ->
                    val action = SecondFragmentDirections.actionSecondFragmentToThirdFragment(producto)
                    findNavController().navigate(action)
                },
                onDeleteClick = { producto ->
                    eliminarProducto(producto)
                }
            )

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = this@SecondFragment.adapter
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error al configurar la lista: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupFab() {
        binding.fabAgregar.setOnClickListener {
            try {
                val action = SecondFragmentDirections.actionSecondFragmentToThirdFragment(null)
                findNavController().navigate(action)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al navegar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupResultListener() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Producto>("producto")
            ?.observe(viewLifecycleOwner) { producto ->
                if (producto.id == 0) {
                    // Nuevo producto
                    val id = db.insertarProducto(producto).toInt()
                    if (id != -1) {
                        productos.add(producto.copy(id = id))
                        adapter.notifyDataSetChanged()
                        Toast.makeText(requireContext(), "Producto guardado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Actualizar producto existente
                    val filasActualizadas = db.actualizarProducto(producto)
                    if (filasActualizadas > 0) {
                        val index = productos.indexOfFirst { it.id == producto.id }
                        if (index != -1) {
                            productos[index] = producto
                            adapter.notifyDataSetChanged()
                            Toast.makeText(requireContext(), "Producto actualizado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }

    private fun eliminarProducto(producto: Producto) {
        val filasEliminadas = db.eliminarProducto(producto.id)
        if (filasEliminadas > 0) {
            productos.remove(producto)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarProductos() {
        try {
            productos.clear()
            val productosDB = db.obtenerProductos()
            if (productosDB.isEmpty()) {
                // Cargar datos de ejemplo solo si la base de datos está vacía
                val productosEjemplo = listOf(
                    Producto(1, "Laptop", "Electrónicos", 999.99, "https://example.com/laptop.jpg"),
                    Producto(2, "Smartphone", "Electrónicos", 499.99, "https://example.com/phone.jpg"),
                    Producto(3, "Auriculares", "Accesorios", 99.99, "https://example.com/headphones.jpg")
                )
                productosEjemplo.forEach { producto ->
                    val id = db.insertarProducto(producto).toInt()
                    if (id != -1) {
                        productos.add(producto.copy(id = id))
                    }
                }
            } else {
                productos.addAll(productosDB)
            }
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error al cargar productos: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}