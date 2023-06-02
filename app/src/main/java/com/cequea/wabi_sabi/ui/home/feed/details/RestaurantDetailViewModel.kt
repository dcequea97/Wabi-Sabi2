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
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _restaurant: MutableStateFlow<Restaurant> =
        MutableStateFlow(
            Restaurant(
                1,
                "El Rinconcito AnDaLuz",
                "https://scontent.fccs3-1.fna.fbcdn.net/v/t1.6435-9/126720125_2704571226461305_2472062523380462448_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=a26aad&_nc_ohc=CXsHyk4_tjEAX9OOqzh&_nc_ht=scontent.fccs3-1.fna&oh=00_AfAI3FGj7iU7Z8rg0_ra51SryjuvRSBHLqzlT3KA1xeYZQ&oe=64989601",
                "data:image/jpeg;base64,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hhntnOM8TUf4paFdVVYleoaunTPWchSzEtWVJLtk8IcknJzLj/3Y9Gzn7GO+f6/UY/Tfj8prW+Gen19R01K6OgI2kubBqRssj07SdwOW+ZuTAo3j34jaLX6NqEpvR96srMK8AqfXDE4wT2mfwP8AFLT6XSVabU12s1ZKhq1QqEzkZywORk+k7LTo6kGErRQPRUUD+Eo3XfhR0/UWm1TZSWOWWsrsY9yQrD5Sfpx9IG3oOu0dS1tH2Vy9OjRrnbGB5jq1NaFWGQQrWt9CBMnxH8Sto9OEo+bVao+XSoGSM8M+PoDgd/mI4xmTHQOg6fQ0eVpqzgZY5OWsbHdm9TwB7CRfSvCrNqz1DXMLL8YrRf6vTLzgL/bbB+8fUk+2Ai/hT06vSpfprF26xLA124gl1bmtkPcpgkf4t3uJ0OUfx/0HUMa9f09iur0gPA7XV92QjsfoPXJHtjB4W+Jmj1ICXsNNcDgpYcKTwPlY/Ung4IwYF/iYaL0cbkdWU+qsCP1E0+odb0tAJu1FVe3vudQR+XeBJRK70PxMmsc/Zq3alcg3sNqMwJG2sHl+3JHH1ligIiICIiAiIgIiICIiAiIgIiICIiAiIgJiapdwYqCwBAOBkA9wD6DgTLEBERAREQPhlB8XfDLSa12uQmi5u7IAVc+7r79skd8S/wAQOHr8Gtap2rrUC+4Fg/gDLH0H4RaSplfVu+pcYO05VMj3A5YduCf1nTYgYKKURVStQqqoCqoAVQBgBQOAAPQTPEQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQERED/2Q==",
                "El Rinconcit andaluz",
                openingHour = 8,
                closeHour = 16,
                workingDays = arrayListOf(1, 2, 3, 5, 6, 7)

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