package com.KevinDevJs.SoftOfManagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.KevinDevJs.SoftOfManagement.adapter.ViewPagerAdapter;
import com.KevinDevJs.SoftOfManagement.model.Categoria;
import com.KevinDevJs.SoftOfManagement.model.Inventario;
import com.KevinDevJs.SoftOfManagement.model.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Inventario inventario;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar inventario con contexto para persistencia
        inventario = new Inventario(this);

        // Obtener referencias a las vistas
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FloatingActionButton fab = findViewById(R.id.fab);

        // Configurar la barra de herramientas
        setSupportActionBar(findViewById(R.id.toolbar));

        // Inicializar datos de prueba solo si no hay datos existentes
        if (inventario.obtenerProductos().isEmpty() && inventario.obtenerCategorias().isEmpty()) {
            inicializarDatosDePrueba();
        }

        // Configurar ViewPager con TabLayout
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // Configurar TabLayout
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Productos");
                    break;
                case 1:
                    tab.setText("Categorías");
                    break;
                case 2:
                    tab.setText("Inventario");
                    break;
            }
        }).attach();

        // Configurar FAB para agregar nuevos elementos
        fab.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            switch (currentItem) {
                case 0:
                    mostrarDialogoAgregarProducto();
                    break;
                case 1:
                    mostrarDialogoAgregarCategoria();
                    break;
                default:
                    Toast.makeText(this, "Opción no disponible", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializarDatosDePrueba() {
        // Categorías de ejemplo
        Categoria categoria1 = new Categoria(1, "Electrónicos", "Productos electrónicos y gadgets");
        Categoria categoria2 = new Categoria(2, "Hogar", "Artículos para el hogar");
        Categoria categoria3 = new Categoria(3, "Ropa", "Prendas de vestir y accesorios");

        // Agregar categorías
        inventario.agregarCategoria(categoria1);
        inventario.agregarCategoria(categoria2);
        inventario.agregarCategoria(categoria3);

        // Productos de ejemplo
        Producto producto1 = new Producto(1, "Smartphone XYZ", "Teléfono de última generación", 599.99, 10, 1);
        Producto producto2 = new Producto(2, "Laptop Pro", "Portátil para trabajo profesional", 1299.99, 5, 1);
        Producto producto3 = new Producto(3, "Mesa de Centro", "Mesa de madera para sala", 149.99, 8, 2);
        Producto producto4 = new Producto(4, "Camiseta Casual", "Camiseta 100% algodón", 19.99, 20, 3);
        Producto producto5 = new Producto(5, "Auriculares Bluetooth", "Auriculares inalámbricos", 89.99, 15, 1);

        // Agregar productos
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        inventario.agregarProducto(producto4);
        inventario.agregarProducto(producto5);
    }

    private void mostrarDialogoAgregarProducto() {
        // Inflar la vista manualmente sin usar viewbinding
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_producto, null);

        // Obtener referencias a los elementos de la vista
        EditText etNombre = dialogView.findViewById(R.id.etNombre);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);
        EditText etPrecio = dialogView.findViewById(R.id.etPrecio);
        EditText etStock = dialogView.findViewById(R.id.etStock);
        Spinner spinnerCategoria = dialogView.findViewById(R.id.spinnerCategoria);

        // Llenar spinner con categorías
        List<Categoria> categorias = inventario.obtenerCategorias();
        List<String> categoriasNombres = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasNombres.add(categoria.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoriasNombres
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        @SuppressLint("NotifyDataSetChanged") AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Agregar Producto")
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

                    int categoriaIndex = spinnerCategoria.getSelectedItemPosition();
                    int categoriaId = 1;

                    if (categoriaIndex >= 0 && categoriaIndex < categorias.size()) {
                        categoriaId = categorias.get(categoriaIndex).getId();
                    }

                    // Generar nuevo ID usando el método del Inventario
                    int newId = inventario.generarNuevoIdProducto();

                    Producto nuevoProducto = new Producto(newId, nombre, descripcion, precio, stock, categoriaId);
                    inventario.agregarProducto(nuevoProducto);

                    // Notificar cambios
                    viewPagerAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    private void mostrarDialogoAgregarCategoria() {
        // Inflar la vista manualmente sin usar viewbinding
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_categoria, null);

        // Obtener referencias a los elementos de la vista
        EditText etNombre = dialogView.findViewById(R.id.etNombre);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);

        @SuppressLint("NotifyDataSetChanged") AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Agregar Categoría")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog1, which) -> {
                    String nombre = etNombre.getText().toString();
                    String descripcion = etDescripcion.getText().toString();

                    // Generar nuevo ID usando el método del Inventario
                    int newId = inventario.generarNuevoIdCategoria();

                    Categoria nuevaCategoria = new Categoria(newId, nombre, descripcion);
                    inventario.agregarCategoria(nuevaCategoria);

                    // Notificar cambios
                    viewPagerAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Categoría agregada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    public Inventario getInventario() {
        return inventario;
    }
} 