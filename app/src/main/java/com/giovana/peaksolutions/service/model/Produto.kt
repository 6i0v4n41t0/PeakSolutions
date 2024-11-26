package com.giovana.peaksolutions.service.model

import com.google.gson.annotations.SerializedName

data class Produto (
    var status: String = "",
    @SerializedName("id_produto")
    var idProduto: Int = 0,
    @SerializedName("tamanho")
    var tamanho: String = "",
    @SerializedName("nome")
    var nome: String = "",
    @SerializedName("id_material")
    var idMaterial: Int = 0,
    @SerializedName("nome_material")
    var nomeMaterial: String = "",
    @SerializedName("quantidade_produto")
    var quantidadeProduto: Int = 0
)
