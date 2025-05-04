package fragment

import adapter.CategoriaAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.KevinDevJs.SoftOfManagement.databinding.FragmentCategoriasBinding

class CategoriasFragment : Fragment() {

    private var _binding: FragmentCategoriasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        adapter = CategoriaAdapter(mainActivity.inventario.obtenerCategorias(), mainActivity)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@CategoriasFragment.adapter
        }

        actualizarVistaVacia()
    }

    override fun onResume() {
        super.onResume()
        actualizarCategorias()
    }

    private fun actualizarCategorias() {
        val mainActivity = activity as MainActivity
        adapter.actualizarLista(mainActivity.inventario.obtenerCategorias())
        actualizarVistaVacia()
    }

    private fun actualizarVistaVacia() {
        if (adapter.itemCount == 0) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 