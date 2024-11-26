package com.giovana.peaksolutions.service.model

import com.google.gson.annotations.SerializedName

data class Material (
    var status: String? = "",
    @SerializedName("id_material")
    var idMaterial: Int = 0,
    @SerializedName("nome_material")
    var nomeMaterial: String = "",
    @SerializedName("cor")
    var cor: String = ""
)