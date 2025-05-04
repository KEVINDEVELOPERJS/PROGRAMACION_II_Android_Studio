package adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.KevinDevJs.SoftOfManagement.databinding.DialogAddProductoBinding
import com.KevinDevJs.SoftOfManagement.databinding.ItemProductoBinding
import model.Producto

class ProductoAdapter(
    private var productos: List<Producto>,
    private val activity: MainActivity
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        with(holder.binding) {
            tvNombre.text = producto.nombre
            tvDescripcion.text = producto.descripcion
            tvPrecio.text = "Precio: $${producto.precio}"
            tvStock.text = "Stock: ${producto.stock}"

            // Categoría
            val categoria = activity.inventario.obtenerCategoria(producto.categoriaId)
            tvCategoria.text = "Categoría: ${categoria?.nombre ?: "Desconocida"}"

            // Botón de editar
            btnEditar.setOnClickListener {
                mostrarDialogoEditarProducto(producto)
            }

            // Botón de eliminar
            btnEliminar.setOnClickListener {
                AlertDialog.Builder(activity)
                    .setTitle("Eliminar Producto")
                    .setMessage("¿Está seguro que desea eliminar ${producto.nombre}?")
                    .setPositiveButton("Sí") { _, _ ->
                        if (activity.inventario.eliminarProducto(producto.id)) {
                            actualizarLista(activity.inventario.obtenerProductos())
                            Toast.makeText(activity, "Producto eliminado", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = productos.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        this.productos = nuevaLista
        notifyDataSetChanged()
    }

    private fun mostrarDialogoEditarProducto(producto: Producto) {
        val dialogBinding = DialogAddProductoBinding.inflate(activity.layoutInflater)

        // Prellenar campos
        dialogBinding.etNombre.setText(producto.nombre)
        dialogBinding.etDescripcion.setText(producto.descripcion)
        dialogBinding.etPrecio.setText(producto.precio.toString())
        dialogBinding.etStock.setText(producto.stock.toString())

        // Llenar spinner con categorías
        val categorias = activity.inventario.obtenerCategorias()
        val categoriasNombres = categorias.map { it.nombre }.toTypedArray()

        // Seleccionar categoría actual
        val categoriaIndex = categorias.indexOfFirst { it.id == producto.categoriaId }
        if (categoriaIndex >= 0) {
            dialogBinding.spinnerCategoria.setSelection(categoriaIndex)
        }

        val dialog = AlertDialog.Builder(activity)
            .setTitle("Editar Producto")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString()
                val descripcion = dialogBinding.etDescripcion.text.toString()
                val precio = dialogBinding.etPrecio.text.toString().toDoubleOrNull() ?: 0.0
                val stock = dialogBinding.etStock.text.toString().toIntOrNull() ?: 0
                val categoriaSelectedIndex = dialogBinding.spinnerCategoria.selectedItemPosition
                val categoriaId =
                    if (categoriaSelectedIndex >= 0 && categoriaSelectedIndex < categorias.size) {
                        categorias[categoriaSelectedIndex].id
                    } else {
                        producto.categoriaId
                    }

                val productoActualizado = producto.copy(
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    stock = stock,
                    categoriaId = categoriaId
                )

                if (activity.inventario.actualizarProducto(productoActualizado)) {
                    actualizarLista(activity.inventario.obtenerProductos())
                    Toast.makeText(activity, "Producto actualizado", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
} 