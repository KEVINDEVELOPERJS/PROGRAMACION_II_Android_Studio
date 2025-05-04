package model

class Inventario {
    private val productos = mutableListOf<Producto>()
    private val categorias = mutableListOf<Categoria>()

    // Métodos para gestionar productos
    fun agregarProducto(producto: Producto) {
        productos.add(producto)
    }

    fun eliminarProducto(id: Int): Boolean {
        return productos.removeIf { it.id == id }
    }

    fun actualizarProducto(producto: Producto): Boolean {
        val index = productos.indexOfFirst { it.id == producto.id }
        if (index != -1) {
            productos[index] = producto
            return true
        }
        return false
    }

    fun obtenerProductos(): List<Producto> = productos.toList()

    fun obtenerProducto(id: Int): Producto? = productos.find { it.id == id }

    fun obtenerProductosPorCategoria(categoriaId: Int): List<Producto> {
        return productos.filter { it.categoriaId == categoriaId }
    }

    // Métodos para gestionar categorías
    fun agregarCategoria(categoria: Categoria) {
        categorias.add(categoria)
    }

    fun eliminarCategoria(id: Int): Boolean {
        return categorias.removeIf { it.id == id }
    }

    fun actualizarCategoria(categoria: Categoria): Boolean {
        val index = categorias.indexOfFirst { it.id == categoria.id }
        if (index != -1) {
            categorias[index] = categoria
            return true
        }
        return false
    }

    fun obtenerCategorias(): List<Categoria> = categorias.toList()

    fun obtenerCategoria(id: Int): Categoria? = categorias.find { it.id == id }
} 