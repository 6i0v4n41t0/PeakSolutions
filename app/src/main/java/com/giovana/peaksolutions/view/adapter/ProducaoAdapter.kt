package com.giovana.peaksolutions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovana.peaksolutions.databinding.ListItemProducaoBinding
import com.giovana.peaksolutions.service.model.Producao

class ProducaoAdapter(producao: List<Producao>?, private val clickListListener: (Producao) -> Unit):
    RecyclerView.Adapter<ProducaoAdapter.ProducaoViewHolder>(){

    private var producaoList: List<Producao> = arrayListOf()

    class ProducaoViewHolder(private val binding: ListItemProducaoBinding) : RecyclerView.ViewHolder(
        binding.root) {
        fun bind(producao: Producao, clickListListener: (Producao) -> Unit) {
            binding.tvProduto.text = producao.nomeProduto
            binding.tvQuantidade.text = producao.quantidadeProducao.toString()

            binding.root.setOnClickListener{
                clickListListener(producao)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProducaoViewHolder {
        //configurar binding da lista
        val listItemProducaoBinding = ListItemProducaoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProducaoViewHolder(listItemProducaoBinding)
    }

    override fun getItemCount(): Int {
        return producaoList.count()
    }

    override fun onBindViewHolder(holder: ProducaoViewHolder, position: Int) {
        holder.bind(producaoList[position], clickListListener)
    }

    fun updateProducao(list: List<Producao>){
        producaoList = list
        notifyDataSetChanged()
    }
}