package com.giovana.peaksolutions.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.giovana.peaksolutions.service.model.Produto
import com.giovana.peaksolutions.service.repository.ProdutoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProdutoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProdutoRepository(application)

    private val mProdutoList = MutableLiveData<List<Produto>>()
    val produtoList: LiveData<List<Produto>> = mProdutoList

    private val mUptadedProduto = MutableLiveData<Produto>()
    val updatedProduto: LiveData<Produto> = mUptadedProduto

    private val mProduto = MutableLiveData<Produto>()
    val produto: LiveData<Produto> = mProduto

    private val mDeleteProduto = MutableLiveData<Boolean>()
    val deleteProduto: LiveData<Boolean> = mDeleteProduto

    private val mCreatedProduto = MutableLiveData<Produto>()
    val cratedProduto: LiveData<Produto> = mCreatedProduto

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val produtos = repository.getProdutos()
                if (produtos.first().status == "Lista vazia"){
                    mErro.postValue("Lista vazia")
                }else{
                    mProdutoList.postValue(produtos)
                }
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun insert(produto: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdProduto = repository.insertProduto(produto)
                mCreatedProduto.postValue(createdProduto)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun update(produto: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedProduto = repository.updatedProduto(produto)
                mUptadedProduto.postValue(updatedProduto)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun updateQuantidadeProduto(id: Int, quantidade: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedQuantidadeProduto = repository.updatedQuantidadeProduto(id, quantidade)
                mUptadedProduto.postValue(updatedQuantidadeProduto)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun getProduto(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mProduto.postValue(repository.getProduto(id))
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mDeleteProduto.postValue(repository.deleteProduto(id))
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }
}