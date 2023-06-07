package com.cequea.wabi_sabi.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.model.Product
import com.cequea.wabi_sabi.data.model.Restaurant
import com.cequea.wabi_sabi.data.repository.ProductRepository
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private var isFirstTime = true

    private val _products: MutableStateFlow<List<Product>> =
        MutableStateFlow(
            emptyList()
        )
    val products = _products.asStateFlow()

    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getCartProducts(){
        if(isFirstTime){
            viewModelScope.launch {
                val result = repository.getCartProducts().data!!
                _products.value = result
                isFirstTime = false
            }
        }
    }

    fun increaseItemCount(idProduct: Long){

    }

    fun decreaseItemCount(idProduct: Long){

    }

    fun removeProduct(idProduct: Long){
        viewModelScope.launch {
            val tempProduct = _products.value.filter {
                it.id != idProduct
            }
            _products.value = tempProduct
        }
    }

}