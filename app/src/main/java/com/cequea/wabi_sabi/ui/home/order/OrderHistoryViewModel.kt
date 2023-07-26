package com.cequea.wabi_sabi.ui.home.order

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.repository.OrderRepository
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.Order
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getOrdersByUserRestaurant() {
        viewModelScope.launch {
            _isLoading.value = true
            dataStoreRepository.getUser().collect { user ->
                when (val response = repository.getOrdersByUserRestaurant(user.id)) {
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

    fun getAllOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.getAllOrders()) {
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

    fun acceptPayment(OrderId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.changeOrderStatus(OrderId, 2)) {
                is Resource.Success -> {
                    _loadError.emit("Pago Aceptado")
                    getAllOrders()
                }

                is Resource.Error -> {
                    _loadError.emit(response.message!!)
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    fun refusePayment(OrderId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.changeOrderStatus(OrderId, 5)) {
                is Resource.Success -> {
                    _loadError.emit("Pago rechazado")
                    getAllOrders()
                }

                is Resource.Error -> {
                    _loadError.emit(response.message!!)
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    fun cancelOrder(OrderId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.changeOrderStatus(OrderId, 5)) {
                is Resource.Success -> {
                    _loadError.emit("Pago rechazado")
                    getAllOrders()
                }

                is Resource.Error -> {
                    _loadError.emit(response.message!!)
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    fun orderPrepared(OrderId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.changeOrderStatus(OrderId, 7)) {
                is Resource.Success -> {
                    _loadError.emit("Orden en preparacion")
                    getAllOrders()
                }

                is Resource.Error -> {
                    _loadError.emit(response.message!!)
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    fun orderSend(OrderId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.changeOrderStatus(OrderId, 3)) {
                is Resource.Success -> {
                    _loadError.emit("Orden Enviada")
                    getAllOrders()
                }

                is Resource.Error -> {
                    _loadError.emit(response.message!!)
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    fun orderReceive(OrderId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.changeOrderStatus(OrderId, 4)) {
                is Resource.Success -> {
                    _loadError.emit("Orden entregada")
                    getAllOrders()
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