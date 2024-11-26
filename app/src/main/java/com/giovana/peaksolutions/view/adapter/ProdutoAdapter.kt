package com.giovana.peaksolutions.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giovana.peaksolutions.databinding.ListItemProdutoBinding
import com.giovana.peaksolutions.service.model.Material
import com.giovana.peaksolutions.service.model.Produto

class ProdutoAdapter(produtos: List<Produto>?, private val clickListener: (Produto) -> Unit) :
    RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    //criar uma lista vazia de pessoas
    private var produtoList: List<Produto> = arrayListOf()

    class ProdutoViewHolder(private val binding: ListItemProdutoBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        //carrega as informaçoes da pessoa na lista
        fun bind(produto: Produto, clickListener: (Produto) -> Unit) {
            binding.tvMaterial.text = produto.idMaterial.toString()
            binding.tvTamanho.text = produto.tamanho
            binding.tvNome.text = produto.nome
            binding.tvMaterial.text = produto.nomeMaterial
            binding.tvQtd.text = produto.quantidadeProduto.toString()

            //configura o click de algum item da lista
            binding.root.setOnClickListener {
                clickListener(produto)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        //configurar o binding da lista
        val listItemProdutoBinding = ListItemProdutoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProdutoViewHolder(listItemProdutoBinding)
    }

    override fun getItemCount(): Int {
        return produtoList.count()
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        holder.bind(produtoList[position], clickListener)
    }

    //carrega a lista de produtos para ser exibidos
    fun updateProduto(list: List<Produto>) {
        produtoList = list
        notifyDataSetChanged()
    }
}