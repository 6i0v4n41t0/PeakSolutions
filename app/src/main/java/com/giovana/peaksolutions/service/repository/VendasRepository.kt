package com.giovana.peaksolutions.service.repository

import android.content.Context
import com.giovana.peaksolutions.service.model.Venda
import com.giovana.peaksolutions.service.repository.remote.RetrofitClient
import com.giovana.peaksolutions.service.repository.remote.VendasService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class VendasRepository(context: Context) {
    private val mRemote = RetrofitClient.createService(VendasService::class.java)
    private val vendaEmpty = Venda("", 0,0,"", 0)

    suspend fun getVendas(): List<Venda> {
        return mRemote.getVendas()
    }

    suspend fun insertVendas(venda: Venda): Venda {
        return mRemote.createVenda(
            id_produto = venda.idProduto.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            quantidade = venda.quantidadeVenda.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        ).body() ?: vendaEmpty
    }

    suspend fun getVendas(id: Int): Venda {
        val response = mRemote.getVendaById(id)
        return if (response.isSuccessful) {
            response.body() ?: vendaEmpty
        } else {
            vendaEmpty
        }
    }

    suspend fun deleteVenda(id: Int): Boolean {
        return mRemote.deleteVendaById(id).isSuccessful
    }
}