package com.giovana.peaksolutions.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.giovana.peaksolutions.service.model.Material
import com.giovana.peaksolutions.service.model.Produto
import com.giovana.peaksolutions.service.repository.MateriaisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MateriaisViewModel (application: Application): AndroidViewModel(application) {
    private val repository = MateriaisRepository(application)

    private val mMaterialList = MutableLiveData<List<Material>>()
    val materialList: LiveData<List<Material>> = mMaterialList

    private val mUptadedMaterial = MutableLiveData<Material>()
    val uptadedMaterial: LiveData<Material> = mUptadedMaterial

    private val mMaterial = MutableLiveData<Material>()
    val material: LiveData<Material> = mMaterial

    private val mDeleteMaterial = MutableLiveData<Boolean>()
    val deleteMaterial: LiveData<Boolean> = mDeleteMaterial

    private val mCreatedMaterial = MutableLiveData<Material>()
    val createdMaterial: MutableLiveData<Material> = mCreatedMaterial

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    fun load(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mMaterialList.postValue(repository.getMateriais())
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun insert(material: Material){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val createdProduto = repository.insertMaterial(material)
                mCreatedMaterial.postValue(createdProduto)
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun update (material: Material){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val updatedProduto = repository.updatedMaterial(material)
                mUptadedMaterial.postValue(updatedProduto)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun getMaterial(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mMaterial.postValue(repository.getMaterial(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mDeleteMaterial.postValue(repository.deleteMaterial(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }
}