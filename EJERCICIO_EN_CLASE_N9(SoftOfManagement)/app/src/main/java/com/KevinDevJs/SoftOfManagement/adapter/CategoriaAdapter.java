package com.KevinDevJs.SoftOfManagement.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KevinDevJs.SoftOfManagement.MainActivity;
import com.KevinDevJs.SoftOfManagement.databinding.DialogAddCategoriaBinding;
import com.KevinDevJs.SoftOfManagement.databinding.ItemCategoriaBinding;
import com.KevinDevJs.SoftOfManagement.model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private final MainActivity activity;
    private List<Categoria> categorias;

    public CategoriaAdapter(List<Categoria> categorias, MainActivity activity) {
        this.categorias = new ArrayList<>(categorias);
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoriaBinding binding = ItemCategoriaBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CategoriaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);

        holder.binding.tvNombre.setText(categoria.getNombre());
        holder.binding.tvDescripcion.setText(categoria.getDescripcion());

        // Contar productos en esta categoría
        int productosEnCategoria = activity.getInventario().obtenerProductosPorCategoria(categoria.getId()).size();
        holder.binding.tvProductosCount.setText("Productos: " + productosEnCategoria);

        // Botón de editar
        holder.binding.btnEditar.setOnClickListener(v -> mostrarDialogoEditarCategoria(categoria));

        // Botón de eliminar
        holder.binding.btnEliminar.setOnClickListener(v -> {
            // Verificar si hay productos en esta categoría
            if (productosEnCategoria > 0) {
                Toast.makeText(
                        activity,
                        "No se puede eliminar: hay " + productosEnCategoria + " productos en esta categoría",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            new AlertDialog.Builder(activity)
                    .setTitle("Eliminar Categoría")
                    .setMessage("¿Está seguro que desea eliminar " + categoria.getNombre() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        if (activity.getInventario().eliminarCategoria(categoria.getId())) {
                            actualizarLista(activity.getInventario().obtenerCategorias());
                            Toast.makeText(activity, "Categoría eliminada", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void actualizarLista(List<Categoria> nuevaLista) {
        this.categorias = new ArrayList<>(nuevaLista);
        notifyDataSetChanged();
    }

    private void mostrarDialogoEditarCategoria(Categoria categoria) {
        DialogAddCategoriaBinding dialogBinding = DialogAddCategoriaBinding.inflate(activity.getLayoutInflater());

        // Prellenar campos
        dialogBinding.etNombre.setText(categoria.getNombre());
        dialogBinding.etDescripcion.setText(categoria.getDescripcion());

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("Editar Categoría")
                .setView(dialogBinding.getRoot())
                .setPositiveButton("Guardar", (dialog1, which) -> {
                    String nombre = dialogBinding.etNombre.getText().toString();
                    String descripcion = dialogBinding.etDescripcion.getText().toString();

                    Categoria categoriaActualizada = new Categoria(
                            categoria.getId(),
                            nombre,
                            descripcion
                    );

                    if (activity.getInventario().actualizarCategoria(categoriaActualizada)) {
                        actualizarLista(activity.getInventario().obtenerCategorias());
                        Toast.makeText(activity, "Categoría actualizada", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoriaBinding binding;

        public CategoriaViewHolder(ItemCategoriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 