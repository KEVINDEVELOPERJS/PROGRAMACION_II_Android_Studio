package com.cristhian.appgestionnotas.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristhian.appgestionnotas.R;
import com.cristhian.appgestionnotas.model.Estudiante;

import java.util.List;

public class EstudianteAdapter extends RecyclerView.Adapter<EstudianteAdapter.ViewHolder> {
    private List<Estudiante> estudianteList;

    public EstudianteAdapter(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.estudiante_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Estudiante estudiante = estudianteList.get(position);
        holder.tvCodigo.setText(estudiante.getCodigo());
        holder.tvNombre.setText(estudiante.getNombre());

    }

    @Override
    public int getItemCount() {
        return estudianteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo, tvNombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tvItemCodigo);
            tvNombre = itemView.findViewById(R.id.tvItemNombre);
        }
    }
}
