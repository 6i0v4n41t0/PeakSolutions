package com.giovana.peaksolutions.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.giovana.peaksolutions.service.model.Venda
import com.giovana.peaksolutions.service.repository.VendasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VendasViewModel(application: Application) : AndroidViewModel(application){
    private val repository = VendasRepository(application)

    private val mUpdateVenda = MutableLiveData<Venda>()
    val updateVenda: LiveData<Venda> = mUpdateVenda

    private val mVendaList = MutableLiveData<List<Venda>>()
    val vendaList: LiveData<List<Venda>> = mVendaList

    private val mCreatedVenda = MutableLiveData<Venda>()
    val createdVenda: LiveData<Venda> = mCreatedVenda

    private val mDeleteVenda = MutableLiveData<Boolean>()
    val deleteVenda: LiveData<Boolean> = mDeleteVenda

    private val mVenda = MutableLiveData<Venda>()
    val venda: LiveData<Venda> = mVenda

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    fun loadVendas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val vendas = repository.getVendas()
                if (vendas.first().status == "Lista vazia"){
                    mErro.postValue(vendas.first().status)
                } else{
                    mVendaList.postValue(vendas)
                }
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }
    fun insert(venda: Venda) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdVenda = repository.insertVendas(venda)
                if (createdVenda.status != "success"){
                    mErro.postValue(createdVenda.status)
                } else{
                    mCreatedVenda.postValue(createdVenda)
                }
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun getVendas(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mVenda.postValue(repository.getVendas(id))
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mDeleteVenda.postValue(repository.deleteVenda(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }
}