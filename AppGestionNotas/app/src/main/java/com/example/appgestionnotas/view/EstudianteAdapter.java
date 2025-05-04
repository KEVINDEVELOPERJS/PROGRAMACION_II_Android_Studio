package com.example.appgestionnotas.view;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appgestionnotas.R;
import com.example.appgestionnotas.model.Estudiante;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para mostrar estudiantes en un RecyclerView
 * Permite mostrar, editar y eliminar estudiantes
 */
public class EstudianteAdapter extends RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder> {
    // Lista de estudiantes que se mostrarán en el RecyclerView
    private List<Estudiante> estudiantes;
    // Interfaz para manejar los eventos de clic en los estudiantes
    private final OnEstudianteClickListener listener;

    /**
     * Interfaz que define los métodos para manejar los eventos de clic en los estudiantes
     */
    public interface OnEstudianteClickListener {
        // Método llamado cuando se hace clic en un estudiante
        void onEstudianteClick(Estudiante estudiante);
        // Método llamado cuando se hace clic en el botón de editar
        void onEstudianteEdit(Estudiante estudiante);
        // Método llamado cuando se hace clic en el botón de eliminar
        void onEstudianteDelete(Estudiante estudiante);
    }

    /**
     * Constructor que inicializa el adaptador con una lista de estudiantes y un listener
     * @param estudiantes Lista de estudiantes a mostrar
     * @param listener Listener para manejar los eventos de clic
     */
    public EstudianteAdapter(List<Estudiante> estudiantes, OnEstudianteClickListener listener) {
        // Crea una nueva lista con los estudiantes proporcionados
        this.estudiantes = new ArrayList<>(estudiantes);
        // Guarda la referencia al listener
        this.listener = listener;
    }

    /**
     * Método llamado para crear un nuevo ViewHolder
     * @param parent Grupo de vistas padre
     * @param viewType Tipo de vista
     * @return Un nuevo ViewHolder
     */
    @NonNull
    @Override
    public EstudianteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout para cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_estudiante, parent, false);
        // Crea y devuelve un nuevo ViewHolder con la vista inflada
        return new EstudianteViewHolder(view);
    }

    /**
     * Método llamado para vincular los datos de un estudiante con un ViewHolder
     * @param holder ViewHolder que se va a actualizar
     * @param position Posición del estudiante en la lista
     */
    @Override
    public void onBindViewHolder(@NonNull EstudianteViewHolder holder, int position) {
        // Obtiene el estudiante en la posición actual
        Estudiante estudiante = estudiantes.get(position);
        // Establece el nombre del estudiante en el TextView correspondiente
        holder.tvNombre.setText(estudiante.getNombre());
        // Establece el código del estudiante en el TextView correspondiente
        holder.tvCodigo.setText(estudiante.getCodigo());
        
        // Configura el evento de clic en el elemento completo
        holder.itemView.setOnClickListener(v -> listener.onEstudianteClick(estudiante));
        
        // Configura el evento de clic en el botón de editar
        holder.btnEditar.setOnClickListener(v -> listener.onEstudianteEdit(estudiante));
        
        // Configura el evento de clic en el botón de eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            // Crea un diálogo de confirmación para eliminar el estudiante
            new AlertDialog.Builder(v.getContext())
                .setTitle(R.string.eliminar_estudiante)
                .setMessage(R.string.confirmar_eliminacion)
                .setPositiveButton(R.string.aceptar, (dialog, which) -> 
                    // Llama al método del listener cuando se confirma la eliminación
                    listener.onEstudianteDelete(estudiante))
                .setNegativeButton(R.string.cancelar, null)
                .show();
        });
    }

    /**
     * Método que devuelve el número de elementos en la lista
     * @return Número de estudiantes
     */
    @Override
    public int getItemCount() {
        return estudiantes.size();
    }

    /**
     * Método para actualizar la lista de estudiantes
     * @param nuevosEstudiantes Nueva lista de estudiantes
     */
    public void actualizarEstudiantes(List<Estudiante> nuevosEstudiantes) {
        // Actualiza la lista de estudiantes con la nueva lista
        this.estudiantes = new ArrayList<>(nuevosEstudiantes);
        // Notifica al adaptador que los datos han cambiado
        notifyDataSetChanged();
    }

    /**
     * Clase interna que representa un ViewHolder para un estudiante
     * Contiene las referencias a las vistas que se actualizarán
     */
    static class EstudianteViewHolder extends RecyclerView.ViewHolder {
        // TextView para mostrar el nombre del estudiante
        TextView tvNombre;
        // TextView para mostrar el código del estudiante
        TextView tvCodigo;
        // Botón para editar el estudiante
        ImageButton btnEditar;
        // Botón para eliminar el estudiante
        ImageButton btnEliminar;

        /**
         * Constructor que inicializa el ViewHolder con una vista
         * @param itemView Vista que representa un elemento de la lista
         */
        EstudianteViewHolder(View itemView) {
            // Llamada al constructor de la clase padre
            super(itemView);
            // Obtiene las referencias a las vistas
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
} 