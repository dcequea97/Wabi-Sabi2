package com.cequea.wabi_sabi.ui.home.feed.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.model.Filter
import com.cequea.wabi_sabi.data.model.Product
import com.cequea.wabi_sabi.data.model.Restaurant
import com.cequea.wabi_sabi.data.model.RestaurantsCollection
import com.cequea.wabi_sabi.data.repository.ProductRepository
import com.cequea.wabi_sabi.data.repository.RestaurantRepository
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _restaurant: MutableStateFlow<Restaurant> =
        MutableStateFlow(
            Restaurant(
                id = 1L,
                name = "La Panaderia del Pueblo",
                backgroundImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-background.jpg",
                profileImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-profile.jpg",
                tagline = "El sabor de casa en cada pan",
                workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                rating = 4.7,
                openingHours = LocalTime.of(6, 0),
                closingHours = LocalTime.of(20, 0)
            )
        )
    val restaurant = _restaurant.asStateFlow()

    private val _products: MutableStateFlow<List<Product>> =
        MutableStateFlow(
            emptyList()
        )
    val products = _products.asStateFlow()

    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {

    }

    fun getRestaurantById(idRestaurant: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.getRestaurantById(idRestaurant)) {
                is Resource.Success -> {
                    _restaurant.value = response.data!!
                }

                is Resource.Error -> {
                    _loadError.value = response.message!!
                }

                is Resource.Loading -> TODO()
            }
            when (val responseProduct = productRepository.getProductsByRestaurant(idRestaurant)) {
                is Resource.Success -> {
                    _products.value = responseProduct.data!!
                }

                is Resource.Error -> {
                    _loadError.value = responseProduct.message!!
                }

                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

}