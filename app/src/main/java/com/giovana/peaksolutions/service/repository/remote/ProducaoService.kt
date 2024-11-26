package com.giovana.peaksolutions.service.repository.remote

import com.giovana.peaksolutions.service.model.Producao
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ProducaoService {
    @GET("selectproducao")
    suspend fun getProducao(): List<Producao>

    @Multipart
    @POST("addproducao")
    suspend fun createProducao(
        @Part("id_produto") id_produto: RequestBody,
        @Part("quantidade_producao") quantidade_producao: RequestBody,
    ): Response<Producao>

    @GET("selectproducao/{producao_id}")
    suspend fun getProducaoById(@Path("producao_id") id: Int): Response<Producao>

    @DELETE("deleteproducao/{producao_id}")
    suspend fun deleteProducaoById(@Path("producao_id") id: Int): Response<Producao>
}