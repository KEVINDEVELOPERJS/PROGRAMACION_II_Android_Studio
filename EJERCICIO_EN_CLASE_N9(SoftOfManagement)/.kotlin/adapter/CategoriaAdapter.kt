package adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.KevinDevJs.SoftOfManagement.databinding.DialogAddCategoriaBinding
import com.KevinDevJs.SoftOfManagement.databinding.ItemCategoriaBinding
import model.Categoria

class CategoriaAdapter(
    private var categorias: List<Categoria>,
    private val activity: MainActivity
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    inner class CategoriaViewHolder(val binding: ItemCategoriaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val binding = ItemCategoriaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]

        with(holder.binding) {
            tvNombre.text = categoria.nombre
            tvDescripcion.text = categoria.descripcion

            // Contar productos en esta categoría
            val productosEnCategoria =
                activity.inventario.obtenerProductosPorCategoria(categoria.id).size
            tvProductosCount.text = "Productos: $productosEnCategoria"

            // Botón de editar
            btnEditar.setOnClickListener {
                mostrarDialogoEditarCategoria(categoria)
            }

            // Botón de eliminar
            btnEliminar.setOnClickListener {
                // Verificar si hay productos en esta categoría
                if (productosEnCategoria > 0) {
                    Toast.makeText(
                        activity,
                        "No se puede eliminar: hay $productosEnCategoria productos en esta categoría",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                AlertDialog.Builder(activity)
                    .setTitle("Eliminar Categoría")
                    .setMessage("¿Está seguro que desea eliminar ${categoria.nombre}?")
                    .setPositiveButton("Sí") { _, _ ->
                        if (activity.inventario.eliminarCategoria(categoria.id)) {
                            actualizarLista(activity.inventario.obtenerCategorias())
                            Toast.makeText(activity, "Categoría eliminada", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = categorias.size

    fun actualizarLista(nuevaLista: List<Categoria>) {
        this.categorias = nuevaLista
        notifyDataSetChanged()
    }

    private fun mostrarDialogoEditarCategoria(categoria: Categoria) {
        val dialogBinding = DialogAddCategoriaBinding.inflate(activity.layoutInflater)

        // Prellenar campos
        dialogBinding.etNombre.setText(categoria.nombre)
        dialogBinding.etDescripcion.setText(categoria.descripcion)

        val dialog = AlertDialog.Builder(activity)
            .setTitle("Editar Categoría")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString()
                val descripcion = dialogBinding.etDescripcion.text.toString()

                val categoriaActualizada = categoria.copy(
                    nombre = nombre,
                    descripcion = descripcion
                )

                if (activity.inventario.actualizarCategoria(categoriaActualizada)) {
                    actualizarLista(activity.inventario.obtenerCategorias())
                    Toast.makeText(activity, "Categoría actualizada", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
} 