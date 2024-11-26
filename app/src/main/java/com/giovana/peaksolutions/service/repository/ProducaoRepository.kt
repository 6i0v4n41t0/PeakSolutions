package com.giovana.peaksolutions.service.repository

import android.content.Context
import com.giovana.peaksolutions.service.model.Producao
import com.giovana.peaksolutions.service.repository.remote.ProducaoService
import com.giovana.peaksolutions.service.repository.remote.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class ProducaoRepository(context: Context){
    private val mRemote = RetrofitClient.createService(ProducaoService::class.java)
    private val producaoEmpty = Producao("",0,0,0, "")

    suspend fun getProducao(): List<Producao> {
        return mRemote.getProducao()
    }

    suspend fun insertProducao(producao: Producao): Producao {
        return mRemote.createProducao(
            id_produto = producao.idProduto.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            quantidade_producao = producao.quantidadeProducao.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
        ).body() ?: producaoEmpty
    }

    suspend fun getProducaoById(id: Int): Producao {
        val response = mRemote.getProducaoById(id)
        return if (response.isSuccessful) {
            response.body() ?: producaoEmpty
        } else {
            producaoEmpty
        }
    }

    suspend fun deleteProducao(id: Int): Boolean {
        return mRemote.deleteProducaoById(id).isSuccessful
    }

}