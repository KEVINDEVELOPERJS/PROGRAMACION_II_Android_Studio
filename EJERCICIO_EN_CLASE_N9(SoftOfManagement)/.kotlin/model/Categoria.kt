package model

data class Categoria(
    val id: Int,
    var nombre: String,
    var descripcion: String
) {
    override fun toString(): String {
        return "Categoria(id=$id, nombre='$nombre')"
    }
} 