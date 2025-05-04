// Define el paquete donde se encuentra esta clase
package com.example.appgestionnotas.view;

// Importaciones necesarias para el funcionamiento de la clase
import android.app.AlertDialog; // Para mostrar diálogos
import android.view.LayoutInflater; // Para inflar layouts
import android.view.View; // Para la vista base
import android.view.ViewGroup; // Para grupos de vistas
import android.widget.EditText; // Para campos de texto editables
import android.widget.ImageButton; // Para botones con iconos
import android.widget.TextView; // Para mostrar texto
import android.widget.Toast; // Para mostrar mensajes breves
import androidx.annotation.NonNull; // Para anotaciones de no nulidad
import androidx.recyclerview.widget.RecyclerView; // Para el adaptador de RecyclerView
import com.example.appgestionnotas.R; // Recursos de la aplicación
import com.example.appgestionnotas.controller.NotaController; // Controlador de notas
import com.example.appgestionnotas.model.Nota; // Modelo de datos Nota
import java.text.SimpleDateFormat; // Para formatear fechas
import java.util.ArrayList; // Para listas dinámicas
import java.util.List; // Para interfaces de listas
import java.util.Locale; // Para configuración regional

// Adaptador personalizado para el RecyclerView que muestra una lista de notas
public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {
    // Lista de notas que se mostrarán en el RecyclerView
    private List<Nota> notas;

    // Formateador de fecha para mostrar las fechas de las notas
    private final SimpleDateFormat dateFormat;

    // Controlador para operaciones CRUD de notas
    private final NotaController notaController;

    // Listener para notificar cuando se realiza una acción sobre una nota
    private final OnNotaActionListener listener;

    // Interfaz para comunicar acciones sobre las notas a la actividad/fragmento
    public interface OnNotaActionListener {
        void onNotaActualizada(); // Método llamado cuando una nota es actualizada
    }

    // Constructor del adaptador
    public NotaAdapter(List<Nota> notas, NotaController notaController, OnNotaActionListener listener) {
        this.notas = new ArrayList<>(notas); // Copia la lista de notas para evitar modificar la original
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()); // Inicializa el formateador de fecha
        this.notaController = notaController; // Asigna el controlador de notas
        this.listener = listener; // Asigna el listener para notificar acciones
    }

    // Crea nuevos ViewHolders (invocado por el RecyclerView)
    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout del item de nota
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(view); // Retorna un nuevo ViewHolder con la vista inflada
    }

    // Reemplaza el contenido de un ViewHolder (invocado por el RecyclerView)
    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota nota = notas.get(position); // Obtiene la nota en la posición actual

        // Configura la visualización de la materia
        String materia = nota.getMateria();
        if (materia != null && !materia.isEmpty()) {
            holder.tvMateria.setText(materia); // Establece el texto de la materia
            holder.tvMateria.setVisibility(View.VISIBLE); // Hace visible el TextView
        } else {
            holder.tvMateria.setVisibility(View.GONE); // Oculta el TextView si no hay materia
        }

        // Configura la visualización del valor de la nota
        holder.tvValorNota.setText(String.format(Locale.getDefault(), "Nota: %.2f", nota.getValor()));

        // Configura la visualización de la fecha de la nota
        holder.tvFechaNota.setText(dateFormat.format(nota.getFecha()));

        // Configura el listener para el botón de editar
        holder.btnEditarNota.setOnClickListener(v -> mostrarDialogoEditarNota(holder, nota));

        // Configura el listener para el botón de eliminar
        holder.btnEliminarNota.setOnClickListener(v -> mostrarDialogoEliminarNota(holder, nota));
    }

    // Retorna el tamaño de la lista de notas (invocado por el RecyclerView)
    @Override
    public int getItemCount() {
        return notas.size();
    }

    // Actualiza la lista de notas y notifica al RecyclerView para refrescar la vista
    public void actualizarNotas(List<Nota> nuevasNotas) {
        this.notas.clear(); // Limpia la lista actual
        this.notas.addAll(nuevasNotas); // Agrega todas las nuevas notas
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
    }

    // Muestra un diálogo para editar una nota existente
    private void mostrarDialogoEditarNota(NotaViewHolder holder, Nota nota) {
        // Crea un constructor de diálogos
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());

        // Infla el layout del diálogo
        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
        View dialogView = inflater.inflate(R.layout.dialog_agregar_materia, null);

        // Obtiene referencias a los campos de texto del diálogo
        EditText etMateria = dialogView.findViewById(R.id.etMateria);
        EditText etNota = dialogView.findViewById(R.id.etNota);

        // Establece los valores actuales de la nota en los campos
        etMateria.setText(nota.getMateria());
        etNota.setText(String.format(Locale.getDefault(), "%.2f", nota.getValor()));

        // Configura el diálogo
        builder.setView(dialogView)
                .setTitle("Editar Nota") // Título del diálogo
                .setPositiveButton("Guardar", (dialog, which) -> {
                    // Obtiene los valores ingresados
                    String materia = etMateria.getText().toString().trim();
                    String notaTexto = etNota.getText().toString().trim();

                    // Valida que los campos no estén vacíos
                    if (materia.isEmpty() || notaTexto.isEmpty()) {
                        Toast.makeText(holder.itemView.getContext(),
                                "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        // Convierte y valida el valor de la nota
                        double valor = Double.parseDouble(notaTexto);
                        if (valor < 0 || valor > 10) {
                            Toast.makeText(holder.itemView.getContext(),
                                    "La nota debe estar entre 0 y 10", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Intenta actualizar la nota usando el controlador
                        boolean exito = notaController.actualizarNota(nota.getId(), materia, valor);
                        if (exito) {
                            Toast.makeText(holder.itemView.getContext(),
                                    R.string.notas, Toast.LENGTH_SHORT).show();
                            listener.onNotaActualizada(); // Notifica que la nota fue actualizada
                        } else {
                            Toast.makeText(holder.itemView.getContext(),
                                    R.string.eliminar_nota, Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Maneja errores de formato numérico
                        Toast.makeText(holder.itemView.getContext(),
                                "Ingrese un valor numérico válido para la nota", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null); // Botón de cancelar

        builder.create().show(); // Muestra el diálogo
    }

    // Muestra un diálogo de confirmación para eliminar una nota
    private void mostrarDialogoEliminarNota(NotaViewHolder holder, Nota nota) {
        new AlertDialog.Builder(holder.itemView.getContext())
                .setTitle(R.string.eliminar_nota) // Título del diálogo desde recursos
                .setMessage(R.string.confirmar_eliminacion_nota) // Mensaje desde recursos
                .setPositiveButton(R.string.aceptar, (dialog, which) -> {
                    // Intenta eliminar la nota usando el controlador
                    boolean exito = notaController.eliminarNota(nota.getId());
                    if (exito) {
                        Toast.makeText(holder.itemView.getContext(),
                                R.string.nota_eliminada, Toast.LENGTH_SHORT).show();
                        listener.onNotaActualizada(); // Notifica que la nota fue eliminada
                    } else {
                        Toast.makeText(holder.itemView.getContext(),
                                R.string.error_eliminar_nota, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancelar, null) // Botón de cancelar desde recursos
                .show(); // Muestra el diálogo
    }

    // Clase ViewHolder que contiene las vistas para cada item de la lista
    static class NotaViewHolder extends RecyclerView.ViewHolder {
        TextView tvMateria; // Muestra la materia de la nota
        TextView tvValorNota; // Muestra el valor de la nota
        TextView tvFechaNota; // Muestra la fecha de la nota
        ImageButton btnEditarNota; // Botón para editar la nota
        ImageButton btnEliminarNota; // Botón para eliminar la nota

        // Constructor que inicializa las vistas
        NotaViewHolder(View itemView) {
            super(itemView);
            // Obtiene referencias a todas las vistas del layout del item
            tvMateria = itemView.findViewById(R.id.tvMateria);
            tvValorNota = itemView.findViewById(R.id.tvValorNota);
            tvFechaNota = itemView.findViewById(R.id.tvFechaNota);
            btnEditarNota = itemView.findViewById(R.id.btnEditarNota);
            btnEliminarNota = itemView.findViewById(R.id.btnEliminarNota);
        }
    }
}