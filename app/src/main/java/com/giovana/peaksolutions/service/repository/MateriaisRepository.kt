package com.giovana.peaksolutions.service.repository

import android.content.Context
import com.giovana.peaksolutions.service.model.Material
import com.giovana.peaksolutions.service.repository.remote.MaterialService
import com.giovana.peaksolutions.service.repository.remote.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class MateriaisRepository(context: Context) {
    private val mRemote = RetrofitClient.createService(MaterialService::class.java)
    private val materiaisEmpty = Material("",0, "", "")

    suspend fun getMateriais(): List<Material>{
        return mRemote.getMateriais()
    }

    suspend fun insertMaterial(material: Material): Material {
        return mRemote.createMaterial(
            nome = material.nomeMaterial.toRequestBody("text/plain".toMediaTypeOrNull()),
            cor = material.cor.toRequestBody(("text/plain".toMediaTypeOrNull()))
        ).body() ?: materiaisEmpty
    }

    suspend fun updatedMaterial(material: Material): Material {
        return mRemote.updatedMaterial(
            materialId = material.idMaterial,
            nome = material.nomeMaterial.toRequestBody("text/plain".toMediaTypeOrNull()),
            cor = material.cor.toRequestBody(("text/plain".toMediaTypeOrNull()))
        ).body() ?: materiaisEmpty
    }

    suspend fun getMaterial(id: Int): Material {
        val response = mRemote.getMaterialById(id)
        return if (response.isSuccessful) {
            response.body() ?: materiaisEmpty
        } else {
            materiaisEmpty
        }
    }

    suspend fun deleteMaterial(id: Int): Boolean {
        return mRemote.deleteMaterialById(id).isSuccessful
    }
}