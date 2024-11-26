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
import com.giovana.peaksolutions.databinding.FragmentProdutosBinding
import com.giovana.peaksolutions.view.adapter.ProdutoAdapter
import com.giovana.peaksolutions.viewmodel.ProdutoViewModel

class ProdutosFragment : Fragment() {

    private val viewModel: ProdutoViewModel by viewModels()

    private lateinit var adapter: ProdutoAdapter

    private var _binding: FragmentProdutosBinding? = null
    private val binding: FragmentProdutosBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProdutosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicar em algum item da lista
        adapter = ProdutoAdapter(viewModel.produtoList.value) { produto ->
            val produtoBundle = Bundle()
            produtoBundle.putInt("produtoId", produto.idProduto)

            arguments?.let {
                arguments = produtoBundle
                if (it.getString("origem") == "Producao"){
                    findNavController().popBackStack(R.id.cadastroProducaoFragment, true)
                    findNavController().navigate(R.id.cadastroProducaoFragment, arguments)
                }else if (it.getString("origem") == "Venda"){
                    findNavController().popBackStack(R.id.cadastroVendaFragment, true)
                    findNavController().navigate(R.id.cadastroVendaFragment, arguments)
                } else{
                    findNavController().navigate(R.id.cadastroItemFragment, arguments)
                }
            } ?: run {
                arguments = produtoBundle
                findNavController().navigate(R.id.cadastroItemFragment, arguments)
            }
        }

        //Configurar a recycler
        val recycler = binding.rvProdutos
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        //observar para adicionar um item na lista quando for adicionado
        viewModel.produtoList.observe(viewLifecycleOwner) {
            adapter.updateProduto(it)
            binding.rvProdutos.visibility = View.VISIBLE
            binding.progress.visibility = View.INVISIBLE
            binding.tvErro.visibility = View.INVISIBLE
        }

        binding.rvProdutos.visibility = View.INVISIBLE
        binding.progress.visibility = View.VISIBLE

        //carregar produtos cadastrados
        viewModel.load()

        viewModel.updatedProduto.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            binding.tvErro.visibility = View.VISIBLE
            if (it == "Lista vazia"){
                binding.tvErro.text = it
            }else{
                binding.tvErro.text = "Ocorreu um erro"
            }
            binding.progress.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.e( "erro","Erro: $it")
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.cadastroItemFragment)
        }
    }
}