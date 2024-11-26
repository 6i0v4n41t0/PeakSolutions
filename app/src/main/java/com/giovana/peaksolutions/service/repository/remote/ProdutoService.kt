package com.giovana.peaksolutions.service.repository.remote

import com.giovana.peaksolutions.service.model.Produto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ProdutoService {

    @GET("selectproduto")
    suspend fun getProdutos(): List<Produto>

    @GET("selectproduto/{id_produto}")
    suspend fun getProdutoById(@Path("id_produto") id: Int): Response<Produto>

    @Multipart
    @POST("addproduto")
    suspend fun createdProduto(
        @Part("nome_produto") nome: RequestBody,
        @Part("tamanho_produto") tamanho: RequestBody,
        @Part("id_material") idMaterial: RequestBody
    ): Response<Produto>

    @Multipart
    @PUT("updateproduto/{id}")
    suspend fun updateProduto(
        @Path("id") produtoId: Int,
        @Part("nome_produto") nome: RequestBody,
        @Part("id_material") idMaterial: RequestBody,
        @Part("tamanho") tamanho: RequestBody,
    ): Response<Produto>

    @Multipart
    @PUT("updatequantidade/{produto_id}")
    suspend fun updateQuantidadeProduto(
        @Path("produto_id") produtoId: Int,
        @Part("quantidade_produto") quantidade: RequestBody,
    ): Response<Produto>

    @DELETE("deleteproduto/{produto_id}")
    suspend fun deleteProdutoById(@Path("produto_id") id: Int):Response<Produto>
}