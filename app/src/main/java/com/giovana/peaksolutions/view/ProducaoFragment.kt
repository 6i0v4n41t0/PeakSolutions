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
import com.giovana.peaksolutions.databinding.FragmentProducaoBinding
import com.giovana.peaksolutions.view.adapter.ProducaoAdapter
import com.giovana.peaksolutions.viewmodel.ProducaoViewModel

class ProducaoFragment: Fragment() {

    //chamar view model
    private val viewModel: ProducaoViewModel by viewModels()

    //chamar o adapter
    private lateinit var adapter: ProducaoAdapter

    private var _binding: FragmentProducaoBinding? = null
    private val  binding: FragmentProducaoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProducaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicar em alguma produção
        adapter = ProducaoAdapter(viewModel.producaoList.value){producao ->
            val producaoBundle = Bundle()
            producaoBundle.putInt("producaoId", producao.idProducao)
            arguments = producaoBundle
            findNavController().navigate(R.id.cadastroProducaoFragment, arguments)
        }

        //Configurar a recycler
        val recycler = binding.rvProduzidos
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.producaoList.observe(viewLifecycleOwner) {
            adapter.updateProducao(it)
            binding.rvProduzidos.visibility = View.VISIBLE
            binding.progress3.visibility = View.INVISIBLE
            binding.tvErro2.visibility = View.INVISIBLE
        }

        binding.rvProduzidos.visibility = View.INVISIBLE
        binding.progress3.visibility = View.VISIBLE

        //carregar entregas cadastradas
        viewModel.loadProducao()

        viewModel.deleteProducao.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Produção excluída", Toast.LENGTH_LONG).show()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            binding.tvErro2.visibility = View.VISIBLE
            if (it == "Lista vazia"){
                binding.tvErro2.text = it
            }else{
                binding.tvErro2.text = "Ocorreu um erro"
            }
            binding.progress3.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.e( "erro","Erro: $it")
        }

        binding.btnAdd3.setOnClickListener {
            findNavController().navigate(R.id.cadastroProducaoFragment)
        }
    }
}