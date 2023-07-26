package com.cequea.wabi_sabi.ui.home_admin.restaurant_request

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.repository.RestaurantRequestRepository
import com.cequea.wabi_sabi.ui.model.RestaurantRequest
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantRequestViewModel @Inject constructor(
    private val repository: RestaurantRequestRepository
) : ViewModel() {


    private val _restaurants: MutableStateFlow<List<RestaurantRequest>> =
        MutableStateFlow(
            emptyList()
        )
    val restaurants = _restaurants.asStateFlow()

    private val _loadError = MutableSharedFlow<String>()
    val loadError = _loadError.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun getAllRestaurantRequests() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.getAllRestaurantRequests()) {
                is Resource.Success -> {
                    _restaurants.value = response.data!!
                }

                is Resource.Error -> {
                    _loadError.emit(response.message!!)
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    fun saveRestaurantRequest(restaurantRequestId: Long){
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.saveRestaurantRequest(restaurantRequestId)) {
                is Resource.Success -> {
                    _loadError.emit("Solicitud Aceptada Satisfactoriamente")
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