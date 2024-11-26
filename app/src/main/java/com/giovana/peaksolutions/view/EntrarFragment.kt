package com.giovana.peaksolutions.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.giovana.peaksolutions.R
import com.giovana.peaksolutions.databinding.FragmentEntrarBinding

class EntrarFragment : Fragment() {

    private var _binding: FragmentEntrarBinding? = null
    private val binding: FragmentEntrarBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntrarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEntrar.setOnClickListener {
            findNavController().navigate(R.id.inicialFragment)
        }
    }

}