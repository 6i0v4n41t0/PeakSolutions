package com.giovana.peaksolutions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.giovana.peaksolutions.R
import com.giovana.peaksolutions.databinding.FragmentEntrarBinding
import com.giovana.peaksolutions.databinding.FragmentInicialBinding

class InicialFragment : Fragment() {

    private var _binding: FragmentInicialBinding? = null
    private val binding: FragmentInicialBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnProdutos.setOnClickListener {
            findNavController().navigate(R.id.produtosFragment)
        }
        binding.btnVendas.setOnClickListener {
            findNavController().navigate(R.id.vendasFragment)
        }
        binding.btnMateriais.setOnClickListener {
            findNavController().navigate(R.id.materiaisFragment)
        }
        binding.btnProducao.setOnClickListener {
            findNavController().navigate(R.id.producaoFragment)
        }
    }
}