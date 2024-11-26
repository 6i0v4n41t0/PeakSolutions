package com.giovana.peaksolutions.service.repository.remote

import com.giovana.peaksolutions.service.model.Material
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

interface MaterialService {

    @GET("material/select")
    suspend fun getMateriais(): List<Material>

    @Multipart
    @POST("material/add")
    suspend fun createMaterial(
        @Part("nome") nome: RequestBody,
        @Part("cor") cor: RequestBody
    ): Response<Material>

    @Multipart
    @PUT("updatematerial/{material_id}")
    suspend fun updatedMaterial(
        @Path("material_id") materialId: Int,
        @Part("nome") nome: RequestBody,
        @Part("cor") cor: RequestBody
    ): Response<Material>

    @GET("material/select/{material_id}")
    suspend fun getMaterialById(@Path("material_id") id: Int): Response<Material>

    @DELETE("material/delete/{material_id}")
    suspend fun deleteMaterialById(@Path("material_id") id: Int):Response<Material>
}