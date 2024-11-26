package com.giovana.peaksolutions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovana.peaksolutions.databinding.ListItemMateriaisBinding
import com.giovana.peaksolutions.service.model.Material

class MaterialAdapter (materiais: List<Material>?, private val clickListener: (Material) -> Unit):
    RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder>(){

    //criar uma lista vazia de pessoas
    private var materialList: List<Material> = arrayListOf()

    class MaterialViewHolder(private val binding: ListItemMateriaisBinding) : RecyclerView.ViewHolder(
        binding.root){
        //carrega as informaçoes da pessoa na lista
        fun bind(material: Material, clickListener: (Material) -> Unit){
            binding.tvMaterial.text = material.nomeMaterial
            binding.tvCor.text = material.cor

            //configura o click de algum item da lista
            binding.root.setOnClickListener{
                clickListener(material)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        //configurar o binding da lista
        val listItemMateriaisBinding = ListItemMateriaisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaterialViewHolder(listItemMateriaisBinding)
    }

    override fun getItemCount(): Int {
        return materialList.count()
    }

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        holder.bind(materialList[position], clickListener)
    }

    //carrega a lista de produtos para ser exibidos
    fun updatedMateriais(list: List<Material>){
        materialList = list
        notifyDataSetChanged()
    }
}