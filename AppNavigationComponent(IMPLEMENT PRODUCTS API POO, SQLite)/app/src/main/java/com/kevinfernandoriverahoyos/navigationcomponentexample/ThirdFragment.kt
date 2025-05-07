package com.kevinfernandoriverahoyos.navigationcomponentexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kevinfernandoriverahoyos.navigationcomponentexample.data.Producto
import com.kevinfernandoriverahoyos.navigationcomponentexample.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    private val args: ThirdFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupButtons()
    }

    private fun setupViews() {
        args.producto?.let { producto ->
            binding.apply {
                editTextNombre.setText(producto.nombre)
                editTextCategoria.setText(producto.categoria)
                editTextPrecio.setText(producto.precio.toString())
                editTextImagenUrl.setText(producto.imagenUrl)
            }
        }
    }

    private fun setupButtons() {
        binding.buttonGuardar.setOnClickListener {
            if (validarCampos()) {
                guardarProducto()
            }
        }

        binding.buttonCancelar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun validarCampos(): Boolean {
        binding.apply {
            val nombre = editTextNombre.text.toString()
            val categoria = editTextCategoria.text.toString()
            val precioStr = editTextPrecio.text.toString()
            val imagenUrl = editTextImagenUrl.text.toString()

            if (nombre.isEmpty()) {
                editTextNombre.error = "El nombre es requerido"
                return false
            }

            if (categoria.isEmpty()) {
                editTextCategoria.error = "La categoría es requerida"
                return false
            }

            if (precioStr.isEmpty()) {
                editTextPrecio.error = "El precio es requerido"
                return false
            }

            try {
                precioStr.toDouble()
            } catch (e: NumberFormatException) {
                editTextPrecio.error = "Precio inválido"
                return false
            }

            if (imagenUrl.isEmpty()) {
                editTextImagenUrl.error = "La URL de la imagen es requerida"
                return false
            }
        }
        return true
    }

    private fun guardarProducto() {
        binding.apply {
            val producto = Producto(
                id = args.producto?.id ?: 0,
                nombre = editTextNombre.text.toString(),
                categoria = editTextCategoria.text.toString(),
                precio = editTextPrecio.text.toString().toDouble(),
                imagenUrl = editTextImagenUrl.text.toString()
            )

            findNavController().previousBackStackEntry?.savedStateHandle?.set("producto", producto)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}