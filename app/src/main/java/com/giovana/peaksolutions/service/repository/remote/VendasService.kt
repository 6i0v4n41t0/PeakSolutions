package com.giovana.peaksolutions.service.repository.remote

import com.giovana.peaksolutions.service.model.Produto
import com.giovana.peaksolutions.service.model.Venda
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface VendasService {
    @GET("selectvenda")
    suspend fun getVendas(): List<Venda>

    @Multipart
    @POST("addvenda")
    suspend fun createVenda(
        @Part("id_produto") id_produto: RequestBody,
        @Part("quantidade") quantidade: RequestBody,
    ): Response<Venda>

    @GET("selectvenda/{id_venda}")
    suspend fun getVendaById(@Path("id_venda") id: Int): Response<Venda>

    @DELETE("deletevenda/{venda_id}")
    suspend fun deleteVendaById(@Path("venda_id") id: Int): Response<Venda>
}