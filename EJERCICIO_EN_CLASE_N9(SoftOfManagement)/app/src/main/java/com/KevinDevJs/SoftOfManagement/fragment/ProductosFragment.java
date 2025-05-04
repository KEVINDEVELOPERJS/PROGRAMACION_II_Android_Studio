package com.KevinDevJs.SoftOfManagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.KevinDevJs.SoftOfManagement.MainActivity;
import com.KevinDevJs.SoftOfManagement.R;
import com.KevinDevJs.SoftOfManagement.adapter.ProductoAdapter;

public class ProductosFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private ProductoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) requireActivity();
        adapter = new ProductoAdapter(mainActivity.getInventario().obtenerProductos(), mainActivity);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        actualizarVistaVacia();
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarProductos();
    }

    private void actualizarProductos() {
        MainActivity mainActivity = (MainActivity) requireActivity();
        adapter.actualizarLista(mainActivity.getInventario().obtenerProductos());
        actualizarVistaVacia();
    }

    private void actualizarVistaVacia() {
        if (adapter.getItemCount() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
} 