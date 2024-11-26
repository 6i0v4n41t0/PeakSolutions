package com.giovana.peaksolutions.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.giovana.peaksolutions.R
import com.giovana.peaksolutions.databinding.FragmentCadastroProducaoBinding
import com.giovana.peaksolutions.service.model.Producao
import com.giovana.peaksolutions.viewmodel.MateriaisViewModel
import com.giovana.peaksolutions.viewmodel.ProducaoViewModel
import com.giovana.peaksolutions.viewmodel.ProdutoViewModel

class CadastroProducaoFragment: Fragment() {

    //chamar view model
    private val viewModel: ProducaoViewModel by viewModels()

    private val viewModelProduto: ProdutoViewModel by viewModels()

    //criar binding
    private var _binding: FragmentCadastroProducaoBinding? = null
    private val binding: FragmentCadastroProducaoBinding get() = _binding!!

    var idProduto = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //configurar binding
        _binding = FragmentCadastroProducaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val idProducao = it.getInt("producaoId")
            val idProduto = it.getInt("produtoId")

            if (idProducao != 0){
                viewModel.getProducao(idProducao)
            } else if(idProduto != 0){
                viewModelProduto.getProduto(idProduto)
            }
        }

        //navegar para a lista de produtos
        binding.btnCadastroProducao.setOnClickListener {
            val quantidade = binding.edtQntd3.editableText.toString().toInt()

            if (idProduto != 0 && quantidade != 0){
                val producao = Producao(
                    idProduto = idProduto,
                    quantidadeProducao = quantidade
                )

                viewModel.producao.value?.let {
                    producao.idProduto = it.idProduto
                    findNavController().navigateUp()
                }?: run {
                    viewModel.insert(producao)
                    //findNavController().navigateUp()
                }
                binding.edtQntd3.editableText.clear()

            }else{
                Toast.makeText(requireContext(),"Digite as informações", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.createdProducao.observe(viewLifecycleOwner){
            viewModelProduto.updateQuantidadeProduto(it.idProduto, it.quantidadeProducao)
        }

        viewModelProduto.updatedProduto.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnExcluirProducao.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão de Producao")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.producao.value?.idProducao ?: 0)
                    findNavController().navigateUp()
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }

        binding.cvSelectProduto.setOnClickListener{
            val materialBundle = Bundle()
            materialBundle.putString("origem", "Producao")
            arguments = materialBundle
            findNavController().navigate(R.id.produtosFragment, arguments)
        }

        viewModel.producao.observe(viewLifecycleOwner) { producao ->
            binding.edtQntd3.setText(producao.quantidadeProducao.toString())

            binding.tvSelectProduto.text = producao.nomeProduto
            idProduto = producao.idProduto

            binding.btnExcluirProducao.visibility = View.VISIBLE
        }

        viewModelProduto.produto.observe(viewLifecycleOwner){
            binding.tvSelectProduto.text = it.nome
            idProduto = it.idProduto
        }

        viewModel.erro.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.e("Erro Cadastro da Venda", it)
        }
    }
}