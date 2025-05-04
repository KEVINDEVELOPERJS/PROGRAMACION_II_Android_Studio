package com.KevinDevJs.SoftOfManagement.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KevinDevJs.SoftOfManagement.MainActivity;
import com.KevinDevJs.SoftOfManagement.R;
import com.KevinDevJs.SoftOfManagement.model.Categoria;
import com.KevinDevJs.SoftOfManagement.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private final MainActivity activity;
    private List<Producto> productos;

    public ProductoAdapter(List<Producto> productos, MainActivity activity) {
        this.productos = new ArrayList<>(productos);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvDescripcion.setText(producto.getDescripcion());
        holder.tvPrecio.setText("Precio: $" + producto.getPrecio());
        holder.tvStock.setText("Stock: " + producto.getStock());

        // Categoría
        Categoria categoria = activity.getInventario().obtenerCategoria(producto.getCategoriaId());
        String categoriaNombre = categoria != null ? categoria.getNombre() : "Desconocida";
        holder.tvCategoria.setText("Categoría: " + categoriaNombre);

        // Botón de editar
        holder.btnEditar.setOnClickListener(v -> mostrarDialogoEditarProducto(producto));

        // Botón de eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Eliminar Producto")
                    .setMessage("¿Está seguro que desea eliminar " + producto.getNombre() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        if (activity.getInventario().eliminarProducto(producto.getId())) {
                            actualizarLista(activity.getInventario().obtenerProductos());
                            Toast.makeText(activity, "Producto eliminado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void actualizarLista(List<Producto> nuevaLista) {
        this.productos = new ArrayList<>(nuevaLista);
        notifyDataSetChanged();
    }

    private void mostrarDialogoEditarProducto(Producto producto) {
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_producto, null);

        // Obtener referencias a los elementos de la vista
        EditText etNombre = dialogView.findViewById(R.id.etNombre);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);
        EditText etPrecio = dialogView.findViewById(R.id.etPrecio);
        EditText etStock = dialogView.findViewById(R.id.etStock);
        Spinner spinnerCategoria = dialogView.findViewById(R.id.spinnerCategoria);

        // Prellenar campos
        etNombre.setText(producto.getNombre());
        etDescripcion.setText(producto.getDescripcion());
        etPrecio.setText(String.valueOf(producto.getPrecio()));
        etStock.setText(String.valueOf(producto.getStock()));

        // Llenar spinner con categorías
        List<Categoria> categorias = activity.getInventario().obtenerCategorias();
        List<String> categoriasNombres = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasNombres.add(categoria.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_spinner_item,
                categoriasNombres
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        // Seleccionar categoría actual
        int categoriaIndex = -1;
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId() == producto.getCategoriaId()) {
                categoriaIndex = i;
                break;
            }
        }

        if (categoriaIndex >= 0) {
            spinnerCategoria.setSelection(categoriaIndex);
        }

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("Editar Producto")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog1, which) -> {
                    String nombre = etNombre.getText().toString();
                    String descripcion = etDescripcion.getText().toString();

                    double precio = 0.0;
                    try {
                        precio = Double.parseDouble(etPrecio.getText().toString());
                    } catch (NumberFormatException e) {
                        // Manejar error de conversión
                    }

                    int stock = 0;
                    try {
                        stock = Integer.parseInt(etStock.getText().toString());
                    } catch (NumberFormatException e) {
                        // Manejar error de conversión
                    }

                    int categoriaSelectedIndex = spinnerCategoria.getSelectedItemPosition();
                    int categoriaId = producto.getCategoriaId();

                    if (categoriaSelectedIndex >= 0 && categoriaSelectedIndex < categorias.size()) {
                        categoriaId = categorias.get(categoriaSelectedIndex).getId();
                    }

                    Producto productoActualizado = new Producto(
                            producto.getId(),
                            nombre,
                            descripcion,
                            precio,
                            stock,
                            categoriaId
                    );

                    if (activity.getInventario().actualizarProducto(productoActualizado)) {
                        actualizarLista(activity.getInventario().obtenerProductos());
                        Toast.makeText(activity, "Producto actualizado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvDescripcion;
        private TextView tvPrecio;
        private TextView tvStock;
        private TextView tvCategoria;
        private Button btnEditar;
        private Button btnEliminar;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvStock = itemView.findViewById(R.id.tvStock);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
} 