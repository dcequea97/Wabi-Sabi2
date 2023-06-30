package com.cequea.wabi_sabi.ui.home.order

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.repository.OrderRepository
import com.cequea.wabi_sabi.ui.model.Product
import com.cequea.wabi_sabi.data.repository.ProductRepository
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.Order
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
class OrderHistoryViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    private val _orders: MutableStateFlow<List<Order>> =
        MutableStateFlow(
            emptyList()
        )
    val orders = _orders.asStateFlow()

    private val _loadError = MutableSharedFlow<String>()
    val loadError = _loadError.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getOrdersByUser() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000L)
            dataStoreRepository.getUser().collect { user ->
                when (val response = repository.getOrdersByUser(user.id)) {
                    is Resource.Success -> {
                        _orders.value = response.data!!
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