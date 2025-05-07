package com.kevinfernandoriverahoyos.navigationcomponentexample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinfernandoriverahoyos.navigationcomponentexample.data.Producto
import com.kevinfernandoriverahoyos.navigationcomponentexample.databinding.ItemProductoBinding

class ProductoAdapter(
    private val productos: List<Producto>,
    private val onItemClick: (Producto) -> Unit,
    private val onDeleteClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(private val binding: ItemProductoBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(producto: Producto) {
            binding.apply {
                textViewNombre.text = producto.nombre
                textViewCategoria.text = producto.categoria
                textViewPrecio.text = String.format("$%.2f", producto.precio)
                
                root.setOnClickListener { onItemClick(producto) }
                buttonBorrar.setOnClickListener { onDeleteClick(producto) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productos[position])
    }

    override fun getItemCount() = productos.size
} 