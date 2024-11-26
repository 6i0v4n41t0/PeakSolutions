package com.giovana.peaksolutions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovana.peaksolutions.databinding.ListItemVendaBinding
import com.giovana.peaksolutions.service.model.Venda

class VendaAdapter(venda: List<Venda>?, private val clickListListener: (Venda) -> Unit):
    RecyclerView.Adapter<VendaAdapter.VendaViewHolder>(){

        private var vendaList: List<Venda> = arrayListOf()

    class VendaViewHolder(private val binding: ListItemVendaBinding) : RecyclerView.ViewHolder(
    binding.root) {
        fun bind(venda: Venda, clickListListener: (Venda) -> Unit) {
            binding.tvNome.text = venda.nomeProduto
            binding.tvQuantidade.text = venda.quantidadeVenda.toString()

            binding.root.setOnClickListener{
                clickListListener(venda)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendaViewHolder {
        //configurar binding da lista
        val listItemVendaBinding = ListItemVendaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VendaViewHolder(listItemVendaBinding)
    }

    override fun getItemCount(): Int {
        return vendaList.count()
    }

    override fun onBindViewHolder(holder: VendaViewHolder, position: Int) {
        holder.bind(vendaList[position], clickListListener)
    }

    fun updateVendas(list: List<Venda>){
        vendaList = list
        notifyDataSetChanged()
    }
}