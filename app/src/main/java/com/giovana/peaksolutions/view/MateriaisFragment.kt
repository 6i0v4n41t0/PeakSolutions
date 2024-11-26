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
import androidx.recyclerview.widget.LinearLayoutManager
import com.giovana.peaksolutions.R
import com.giovana.peaksolutions.databinding.FragmentCadastroMaterialBinding
import com.giovana.peaksolutions.databinding.FragmentMateriaisBinding
import com.giovana.peaksolutions.service.model.Material
import com.giovana.peaksolutions.view.adapter.MaterialAdapter
import com.giovana.peaksolutions.viewmodel.MateriaisViewModel

class MateriaisFragment : Fragment() {

    private val viewModel: MateriaisViewModel by viewModels()

    private lateinit var adapter: MaterialAdapter

    private var _binding: FragmentMateriaisBinding? = null
    private val binding: FragmentMateriaisBinding get() = _binding!!

    private var _bindingDialog: FragmentCadastroMaterialBinding? = null
    private val bindingDialog: FragmentCadastroMaterialBinding get() = _bindingDialog!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMateriaisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //clicar em algum item da lista
        adapter = MaterialAdapter(viewModel.materialList.value) { material ->
            val materialBundle = Bundle()
            materialBundle.putInt("materialId", material.idMaterial)

            arguments?.let {
                arguments = materialBundle
                findNavController().popBackStack(R.id.cadastroItemFragment,true)
                findNavController().navigate(R.id.cadastroItemFragment,arguments)
            } ?: run {
                openDialogMaterial(material.idMaterial)
            }
        }

        //Configurar a recycler
        val recycler = binding.rvMateriais
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        //observar para adicionar um item na lista quando for adicionado
        viewModel.materialList.observe(viewLifecycleOwner) {
            adapter.updatedMateriais(it)
            binding.progress.visibility = View.GONE
        }

        //carregar produtos cadastradas
        viewModel.load()

        viewModel.uptadedMaterial.observe(viewLifecycleOwner) {
            binding.progress.visibility = View.VISIBLE
            viewModel.load()
        }

        viewModel.deleteMaterial.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Material excluído", Toast.LENGTH_LONG).show()
            viewModel.load()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.e("erro", "Erro Materiais: $it")
        }

        viewModel.material.observe(viewLifecycleOwner) {
            bindingDialog.edtMaterial.setText(it.nomeMaterial)
            bindingDialog.edtCor.setText(it.cor)
        }

        viewModel.createdMaterial.observe(viewLifecycleOwner) {
            viewModel.load()
        }

        binding.add2.setOnClickListener {
            openDialogMaterial()
        }
    }

    private fun openDialogMaterial(id: Int = 0) {
        _bindingDialog =
            FragmentCadastroMaterialBinding.inflate(layoutInflater, binding.root, false)

        if (id != 0) {
            viewModel.getMaterial(id)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("CADASTRO DE MATERIAIS")
            .setView(bindingDialog.root)
            .setNegativeButton("Cancelar") { _, _ ->

            }
            .setNeutralButton("Excluir"){ _, _ ->
                viewModel.delete(id)
            }
            .setPositiveButton("Salvar") { t, i ->
                if (id == 0) {
                    viewModel.insert(
                        Material(
                            nomeMaterial = bindingDialog.edtMaterial.text.toString(),
                            cor = bindingDialog.edtCor.text.toString()
                        )
                    )
                } else {
                    viewModel.update(
                        Material(
                            idMaterial = id,
                            nomeMaterial = bindingDialog.edtMaterial.text.toString(),
                            cor = bindingDialog.edtCor.text.toString()
                        )
                    )
                }
            }
            .show()
    }
}