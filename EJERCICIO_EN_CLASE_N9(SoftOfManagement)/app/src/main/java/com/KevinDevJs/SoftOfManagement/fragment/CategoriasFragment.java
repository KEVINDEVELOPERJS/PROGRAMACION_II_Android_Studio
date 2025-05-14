package com.KevinDevJs.SoftOfManagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.KevinDevJs.SoftOfManagement.MainActivity;
import com.KevinDevJs.SoftOfManagement.adapter.CategoriaAdapter;
import com.KevinDevJs.SoftOfManagement.databinding.FragmentCategoriasBinding;

public class CategoriasFragment extends Fragment {

    private FragmentCategoriasBinding binding;
    private CategoriaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) requireActivity();
        adapter = new CategoriaAdapter(mainActivity.getInventario().obtenerCategorias(), mainActivity);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        actualizarVistaVacia();
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarCategorias();
    }

    private void actualizarCategorias() {
        MainActivity mainActivity = (MainActivity) requireActivity();
        adapter.actualizarLista(mainActivity.getInventario().obtenerCategorias());
        actualizarVistaVacia();
    }

    private void actualizarVistaVacia() {
        if (adapter.getItemCount() == 0) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 