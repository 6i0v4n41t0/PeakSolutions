package com.giovana.peaksolutions.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.giovana.peaksolutions.R
import com.giovana.peaksolutions.databinding.FragmentVendasBinding
import com.giovana.peaksolutions.view.adapter.VendaAdapter
import com.giovana.peaksolutions.viewmodel.VendasViewModel

class VendasFragment : Fragment(){

    //chamar view model
    private val viewModel: VendasViewModel by viewModels()

    //chamar o adapter
    private lateinit var adapter: VendaAdapter

    private var _binding: FragmentVendasBinding? = null
    private val  binding: FragmentVendasBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVendasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicar em alguma venda
        adapter = VendaAdapter(viewModel.vendaList.value){venda ->
            val vendaBundle = Bundle()
            vendaBundle.putInt("vendaId", venda.idVenda)
            arguments = vendaBundle
            findNavController().navigate(R.id.cadastroVendaFragment, arguments)
        }

        //Configurar a recycler
        val recycler = binding.rvVendidos
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.vendaList.observe(viewLifecycleOwner) {
            adapter.updateVendas(it)
            binding.rvVendidos.visibility = View.VISIBLE
            binding.progress2.visibility = View.INVISIBLE
            binding.tvErro3.visibility = View.INVISIBLE
        }

        binding.rvVendidos.visibility = View.INVISIBLE
        binding.progress2.visibility = View.VISIBLE

        //carregar entregas cadastradas
        viewModel.loadVendas()

        viewModel.deleteVenda.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Venda excluída", Toast.LENGTH_LONG).show()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            binding.tvErro3.visibility = View.VISIBLE
            if (it == "Lista vazia"){
                binding.tvErro3.text = it
            } else{
                binding.tvErro3.text = "Ocorreu um erro"
            }
            binding.progress2.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.e( "erro","Erro Vendas: $it")
        }

        binding.btnAdd2.setOnClickListener {
            findNavController().navigate(R.id.cadastroVendaFragment)
        }
    }
}