package com.giovana.peaksolutions.service.model

import com.google.gson.annotations.SerializedName

data class Producao (
    var status: String = "",
    @SerializedName("id_producao")
    var idProducao: Int = 0,
    @SerializedName("id_produto")
    var idProduto: Int = 0,
    @SerializedName("quantidade_producao")
    var quantidadeProducao: Int = 0,
    @SerializedName("nome_produto")
    var nomeProduto: String = ""
)