package com.cequea.wabi_sabi.ui.home_provider.edit_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.ui.model.Product
import com.cequea.wabi_sabi.data.repository.ProductRepository
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _isRegisteredSuccess = MutableLiveData<Boolean>()
    val isRegisteredSuccess: LiveData<Boolean>
        get() = _isRegisteredSuccess

    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getProductById(idProduct: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.getProductById(idProduct)) {
                is Resource.Success -> {
                    _product.value = response.data!!
                }

                is Resource.Error -> {
                    //_loadError.value = response.message!!
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.updateProduct(product)) {
                is Resource.Success -> {
                    _loadError.emit("Producto Actualizado")
                    _product.value = response.data!!
                }

                is Resource.Error -> {
                    //_loadError.value = response.message!!
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

}