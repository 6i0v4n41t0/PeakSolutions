package com.giovana.peaksolutions.service.repository

import android.content.Context
import com.giovana.peaksolutions.service.model.Produto
import com.giovana.peaksolutions.service.repository.remote.ProdutoService
import com.giovana.peaksolutions.service.repository.remote.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class ProdutoRepository(context: Context) {
    private val mRemote = RetrofitClient.createService(ProdutoService::class.java)
    private val produtoEmpty = Produto("", 0, "", "", 0)

    suspend fun getProdutos(): List<Produto> {
        return mRemote.getProdutos()
    }

    suspend fun insertProduto(produto: Produto): Produto {
        return mRemote.createdProduto(
            nome = produto.nome.toRequestBody("text/plain".toMediaTypeOrNull()),
            tamanho = produto.tamanho.toRequestBody("text/plain".toMediaTypeOrNull()),
            idMaterial = produto.idMaterial.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        ).body() ?: produtoEmpty
    }

    suspend fun getProduto(id: Int): Produto {
        val response = mRemote.getProdutoById(id = id)
        return if (response.isSuccessful) {
            response.body() ?: produtoEmpty
        } else {
            produtoEmpty
        }
    }

    suspend fun updatedProduto(produto: Produto): Produto {
        return mRemote.updateProduto(
            produtoId = produto.idProduto,
            nome = produto.nome.toRequestBody("text/plain".toMediaTypeOrNull()),
            tamanho = produto.tamanho.toRequestBody("text/plain".toMediaTypeOrNull()),
            idMaterial = produto.idMaterial.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        ).body() ?: produtoEmpty
    }

    suspend fun updatedQuantidadeProduto(id: Int, quantidade: Int): Produto {
        return mRemote.updateQuantidadeProduto(
            produtoId = id,
            quantidade = quantidade.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        ).body() ?: produtoEmpty
    }

    suspend fun deleteProduto(id: Int): Boolean {
        return mRemote.deleteProdutoById(id).isSuccessful
    }

}