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
import com.giovana.peaksolutions.databinding.FragmentCadastroMaterialBinding
import com.giovana.peaksolutions.databinding.FragmentCadastroitemBinding
import com.giovana.peaksolutions.databinding.FragmentMateriaisBinding
import com.giovana.peaksolutions.service.model.Material
import com.giovana.peaksolutions.service.model.Produto
import com.giovana.peaksolutions.viewmodel.MateriaisViewModel
import com.giovana.peaksolutions.viewmodel.ProdutoViewModel

class CadastroItemFragment : Fragment() {

    //chamar view model
    private val viewModel: ProdutoViewModel by viewModels()

    private val viewModelMaterial: MateriaisViewModel by viewModels()

    //criar binding
    private var _binding: FragmentCadastroitemBinding? = null
    private val binding: FragmentCadastroitemBinding get() = _binding!!

    var idMaterial = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //configurar binding
        _binding = FragmentCadastroitemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //carregar o item caso seja selecionado
        arguments?.let {
            val idMaterial = it.getInt("materialId")
            val idProduto = it.getInt("produtoId")

            if (idMaterial != 0){
                viewModelMaterial.getMaterial(idMaterial)
            }else if (idProduto != 0){
                viewModel.getProduto(idProduto)
            }
        }

        //navegar para a lista de produtos
        binding.btnCadastroItem.setOnClickListener {
            val nome = binding.edtNome.editableText.toString()
            val tamanho = binding.edtTamanho.editableText.toString()

            if (nome != "" && tamanho != "" && idMaterial != 0) {
                val produto = Produto(
                    nome = nome,
                    tamanho = tamanho,
                    idMaterial = idMaterial,
                    quantidadeProduto = 0
                )

                viewModel.produto.value?.let {
                    produto.idProduto = it.idProduto
                    viewModel.update(produto)
                } ?: run {
                    viewModel.insert(produto)
                }
                binding.edtTamanho.editableText.clear()
                binding.edtNome.editableText.clear()

                findNavController().navigateUp()

            } else {
                Toast.makeText(requireContext(), "Digite as informações", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnExcluirItem.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão de Item")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.produto.value?.idProduto ?: 0)
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }

        binding.cvSelectMaterial.setOnClickListener{
            val materialBundle = Bundle()
            materialBundle.putString("origem", "P")
            arguments = materialBundle
            findNavController().navigate(R.id.materiaisFragment, arguments)
        }

        viewModel.produto.observe(viewLifecycleOwner) { produto ->
            binding.edtNome.setText(produto.nome)
            binding.edtTamanho.setText(produto.tamanho)

            viewModelMaterial.getMaterial(produto.idMaterial)

            binding.btnExcluirItem.visibility = View.VISIBLE
        }

        viewModel.deleteProduto.observe(viewLifecycleOwner){
            findNavController().navigateUp()
        }

        viewModelMaterial.material.observe(viewLifecycleOwner){
            binding.tvSelectMaterial.text = it.nomeMaterial
            idMaterial = it.idMaterial
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.e("Erro Cadastro de Item", it)
        }
    }
}