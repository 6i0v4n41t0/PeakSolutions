package com.giovana.peaksolutions.service.model

import com.google.gson.annotations.SerializedName

data class Venda (
    var status: String = "",
    @SerializedName("id_venda")
    var idVenda: Int = 0,
    @SerializedName("id_produto")
    var idProduto: Int = 0,
    @SerializedName("nome_produto")
    var nomeProduto: String = "",
    @SerializedName("quantidade_venda")
    var quantidadeVenda: Int = 0
)