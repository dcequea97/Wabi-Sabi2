package com.cequea.wabi_sabi.ui.home.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.ui.model.Product
import com.cequea.wabi_sabi.data.repository.ProductRepository
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    private val _products: MutableStateFlow<List<Product>> =
        MutableStateFlow(
            emptyList()
        )
    val products = _products.asStateFlow()

    private val _loadError = MutableSharedFlow<String>()
    val loadError = _loadError.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getCartProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000L)
            dataStoreRepository.getUser().collect { user ->
                when (val response = repository.getCartProducts(user.id)) {
                    is Resource.Success -> {
                        _products.value = response.data!!
                    }

                    is Resource.Error -> {
                        _loadError.emit(response.message!!)
                    }

                    is Resource.Loading -> TODO()
                }
                _isLoading.value = false
            }
        }
    }

    fun increaseItemCount(idProduct: Long, newCountInCart: Int) {
        modifyItemCount(idProduct, newCountInCart)

    }

    fun decreaseItemCount(idProduct: Long, newCountInCart: Int) {
        modifyItemCount(idProduct, newCountInCart)
    }

    fun removeProduct(idProduct: Long) {
        viewModelScope.launch {
            if (isLoading.value) {
                _loadError.emit("Por Favor Espere")
            } else {
                _isLoading.value = true
                dataStoreRepository.getUser().collect { user ->
                    when (val response = repository.deleteCartProduct(user.id, idProduct)) {
                        is Resource.Success -> {
                            getCartProducts()
                        }

                        is Resource.Error -> {
                            _loadError.emit(response.message!!)
                        }

                        is Resource.Loading -> TODO()
                    }
                    _isLoading.value = false
                }
            }
        }
    }

    private fun modifyItemCount(idProduct: Long, newCountInCart: Int) {
        viewModelScope.launch {
            if (isLoading.value) {
                _loadError.emit("Por Favor Espere")
            } else {
                _isLoading.value = true
                dataStoreRepository.getUser().collect { user ->
                    delay(1000L)
                    when (val response =
                        repository.updateCart(idProduct, user.id, newCountInCart)) {
                        is Resource.Success -> {

                            // Find the product to update based on its id
                            val productToUpdate = _products.value.firstOrNull { it.id == idProduct }

                            if (productToUpdate != null) {
                                // Update the product
                                val updatedProducts = _products.value.map { product ->
                                    if (product.id == idProduct) {
                                        product.countInCart = newCountInCart
                                        product
                                    } else {
                                        product
                                    }
                                }
                                _products.value = updatedProducts
                            }
                        }

                        is Resource.Error -> {
                            _loadError.emit(response.message!!)
                        }

                        is Resource.Loading -> TODO()
                    }
                    _isLoading.value = false
                }
            }
        }
    }

}