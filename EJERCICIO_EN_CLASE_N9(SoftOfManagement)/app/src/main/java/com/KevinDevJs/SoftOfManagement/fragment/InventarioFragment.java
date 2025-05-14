package com.KevinDevJs.SoftOfManagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.KevinDevJs.SoftOfManagement.MainActivity;
import com.KevinDevJs.SoftOfManagement.databinding.FragmentInventarioBinding;
import com.KevinDevJs.SoftOfManagement.model.Inventario;
import com.KevinDevJs.SoftOfManagement.model.Producto;

import java.util.List;
import java.util.Locale;

public class InventarioFragment extends Fragment {

    private FragmentInventarioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInventarioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actualizarResumen();
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarResumen();
    }

    private void actualizarResumen() {
        MainActivity mainActivity = (MainActivity) requireActivity();
        Inventario inventario = mainActivity.getInventario();

        // Obtener datos
        int totalProductos = inventario.obtenerProductos().size();
        int totalCategorias = inventario.obtenerCategorias().size();

        double valorTotal = 0;
        int stockTotal = 0;

        List<Producto> productos = inventario.obtenerProductos();
        for (Producto producto : productos) {
            valorTotal += producto.getPrecio() * producto.getStock();
            stockTotal += producto.getStock();
        }

        // Mostrar estadísticas
        binding.tvTotalProductos.setText("Total de Productos: " + totalProductos);
        binding.tvTotalCategorias.setText("Total de Categorías: " + totalCategorias);
        binding.tvValorInventario.setText("Valor Total del Inventario: $" + String.format(Locale.US, "%.2f", valorTotal));
        binding.tvTotalStock.setText("Total de Unidades en Stock: " + stockTotal);

        // Producto con mayor stock
        Producto productoMayorStock = null;
        int maxStock = 0;

        for (Producto producto : productos) {
            if (producto.getStock() > maxStock) {
                maxStock = producto.getStock();
                productoMayorStock = producto;
            }
        }

        if (productoMayorStock != null) {
            binding.tvMayorStock.setText("Producto con mayor stock: " + productoMayorStock.getNombre() +
                    " (" + productoMayorStock.getStock() + " unidades)");
        } else {
            binding.tvMayorStock.setText("No hay productos disponibles");
        }

        // Producto más valioso
        Producto productoMasValioso = null;
        double maxPrecio = 0;

        for (Producto producto : productos) {
            if (producto.getPrecio() > maxPrecio) {
                maxPrecio = producto.getPrecio();
                productoMasValioso = producto;
            }
        }

        if (productoMasValioso != null) {
            binding.tvMasValioso.setText("Producto más valioso: " + productoMasValioso.getNombre() +
                    " ($" + productoMasValioso.getPrecio() + ")");
        } else {
            binding.tvMasValioso.setText("No hay productos disponibles");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 