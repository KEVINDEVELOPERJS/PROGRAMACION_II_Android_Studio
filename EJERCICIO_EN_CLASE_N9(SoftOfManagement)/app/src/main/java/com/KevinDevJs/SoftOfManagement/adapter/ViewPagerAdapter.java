package com.KevinDevJs.SoftOfManagement.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.KevinDevJs.SoftOfManagement.fragment.CategoriasFragment;
import com.KevinDevJs.SoftOfManagement.fragment.InventarioFragment;
import com.KevinDevJs.SoftOfManagement.fragment.ProductosFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ProductosFragment();
            case 1:
                return new CategoriasFragment();
            case 2:
                return new InventarioFragment();
            default:
                throw new IllegalArgumentException("Posición inválida: " + position);
        }
    }
} 