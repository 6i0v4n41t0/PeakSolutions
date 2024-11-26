package com.giovana.peaksolutions.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.giovana.peaksolutions.R
import com.giovana.peaksolutions.databinding.FragmentCadastroVendaBinding
import com.giovana.peaksolutions.service.model.Venda
import com.giovana.peaksolutions.viewmodel.ProdutoViewModel
import com.giovana.peaksolutions.viewmodel.VendasViewModel

class CadastroVendaFragment : Fragment() {

    //chamar view model
    private val viewModel: VendasViewModel by viewModels()

    private val  viewModelProduto: ProdutoViewModel by viewModels()
    //criar binding
    private var _binding: FragmentCadastroVendaBinding? = null
    private val binding: FragmentCadastroVendaBinding get() = _binding!!

    var idProduto = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //configurar binding
        _binding = FragmentCadastroVendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val idVenda = it.getInt("vendaId")
            val idProduto = it.getInt("produtoId")
            if (idVenda != 0){
                viewModel.getVendas(idVenda)
            } else if (idProduto != 0){
                viewModelProduto.getProduto(idProduto)
            }
        }

        //navegar para a lista de produtos
        binding.btnCadastroItem.setOnClickListener {
            val quantidade = binding.edtQntd2.editableText.toString().toInt()

            if (idProduto != 0 && quantidade != 0){
                val venda = Venda(
                    idProduto = idProduto,
                    quantidadeVenda = quantidade
                )

                viewModel.venda.value?.let {
                    venda.idProduto = it.idProduto
                    findNavController().navigateUp()
                }?: run {
                    viewModel.insert(venda)
                }
                binding.edtQntd2.editableText.clear()

            }else{
                Toast.makeText(requireContext(),"Digite as informações", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnExcluirVenda.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão de Venda")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.venda.value?.idVenda ?: 0)
                    findNavController().navigateUp()
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }

        binding.cvSelectProduto2.setOnClickListener {
            val materialBundle = Bundle()
            materialBundle.putString("origem", "Venda")
            arguments = materialBundle
            findNavController().navigate(R.id.produtosFragment, arguments)
        }

        viewModel.createdVenda.observe(viewLifecycleOwner){

            val qtdVenda = it.quantidadeVenda * -1
            viewModelProduto.updateQuantidadeProduto(it.idProduto, qtdVenda)
        }

        viewModelProduto.updatedProduto.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.venda.observe(viewLifecycleOwner) { venda ->
            binding.edtQntd2.setText(venda.quantidadeVenda.toString())
            binding.tvSelectProduto2.text = venda.nomeProduto
            idProduto = venda.idProduto

            binding.btnExcluirVenda.visibility = View.VISIBLE
        }

        viewModelProduto.produto.observe(viewLifecycleOwner){
            binding.tvSelectProduto2.text = it.nome
            idProduto = it.idProduto
        }

        viewModel.erro.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.e("Erro Cadastro da Venda", it)
        }
    }
}