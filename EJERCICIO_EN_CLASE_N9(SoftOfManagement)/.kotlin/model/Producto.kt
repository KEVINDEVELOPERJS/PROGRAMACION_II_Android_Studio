package model

data class Producto(
    val id: Int,
    var nombre: String,
    var descripcion: String,
    var precio: Double,
    var stock: Int,
    var categoriaId: Int,
    var imagenUrl: String? = null
) {
    override fun toString(): String {
        return "Producto(id=$id, nombre='$nombre', precio=$precio, stock=$stock)"
    }
} 