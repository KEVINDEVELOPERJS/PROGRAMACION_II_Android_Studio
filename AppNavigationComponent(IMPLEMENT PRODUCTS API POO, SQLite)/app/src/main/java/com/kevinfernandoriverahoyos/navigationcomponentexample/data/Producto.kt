package com.kevinfernandoriverahoyos.navigationcomponentexample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val imagenUrl: String
) : Parcelable 