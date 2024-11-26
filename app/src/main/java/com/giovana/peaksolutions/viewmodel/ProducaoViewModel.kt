package com.giovana.peaksolutions.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.giovana.peaksolutions.service.model.Producao
import com.giovana.peaksolutions.service.model.Venda
import com.giovana.peaksolutions.service.repository.ProducaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProducaoViewModel (application: Application) : AndroidViewModel (application){

    private val repository = ProducaoRepository(application)

    private val mUpdateProducao = MutableLiveData<Producao>()
    val updateProducao: LiveData<Producao> = mUpdateProducao

    private val mProducaoList = MutableLiveData<List<Producao>>()
    val producaoList: LiveData<List<Producao>> = mProducaoList

    private val mCreatedProducao = MutableLiveData<Producao>()
    val createdProducao: LiveData<Producao> = mCreatedProducao

    private val mDeleteProducao = MutableLiveData<Boolean>()
    val deleteProducao: LiveData<Boolean> = mDeleteProducao

    private val mProducao = MutableLiveData<Producao>()
    val producao: LiveData<Producao> = mProducao

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    fun loadProducao() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val producao = repository.getProducao()
                if (producao.first().status == "Lista vazia"){
                    mErro.postValue(producao.first().status)
                } else{
                    mProducaoList.postValue(producao)
                }
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }
    fun insert(producao: Producao) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdProducao = repository.insertProducao(producao)
                mCreatedProducao.postValue(createdProducao)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun getProducao(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mProducao.postValue(repository.getProducaoById(id))
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mDeleteProducao.postValue(repository.deleteProducao(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }
}