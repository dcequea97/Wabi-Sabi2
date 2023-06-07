package com.cequea.wabi_sabi.ui.home.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.data.model.Filter
import com.cequea.wabi_sabi.data.model.RestaurantsCollection
import com.cequea.wabi_sabi.data.repository.RestaurantRepository
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    repository: RestaurantRepository
) : ViewModel() {

    private val _restaurantsList: MutableStateFlow<List<RestaurantsCollection>> =
        MutableStateFlow(emptyList())
    val restaurantsList = _restaurantsList.asStateFlow()

    private val _filtersList: MutableStateFlow<List<Filter>> =
        MutableStateFlow(emptyList())
    val filtersList = _filtersList.asStateFlow()

    private val _priceFiltersList: MutableStateFlow<List<Filter>> =
        MutableStateFlow(emptyList())
    val priceFiltersList = _priceFiltersList.asStateFlow()

    private val _categoryFiltersList: MutableStateFlow<List<Filter>> =
        MutableStateFlow(emptyList())
    val categoryFiltersList = _categoryFiltersList.asStateFlow()

    private val _lifeStyleFiltersList: MutableStateFlow<List<Filter>> =
        MutableStateFlow(emptyList())
    val lifeStyleFiltersList = _lifeStyleFiltersList.asStateFlow()

    private val _sortDefaultFiltersList: MutableStateFlow<String> =
        MutableStateFlow("")
    val sortDefaultFiltersList = _sortDefaultFiltersList.asStateFlow()

    private val _sortFiltersList: MutableStateFlow<List<Filter>> =
        MutableStateFlow(emptyList())
    val sortFiltersList = _sortFiltersList.asStateFlow()


    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getAllFilters(repository)
            when (val response = repository.getRestaurants()) {
                is Resource.Success -> {
                    _restaurantsList.value = response.data!!
                }
                is Resource.Error -> {
                    _loadError.value = response.message!!
                }
                is Resource.Loading -> TODO()
            }
            _isLoading.value = false
        }
    }

    private fun getAllFilters(repository: RestaurantRepository){
        viewModelScope.launch {
            _filtersList.value = repository.getFilters()
            _priceFiltersList.value = repository.getPriceFilters()
            _categoryFiltersList.value = repository.getCategoryFilters()
            _lifeStyleFiltersList.value = repository.getLifeStyleFilters()
            val sortFilters = repository.getSortFilters()
            _sortDefaultFiltersList.value = sortFilters[0].name
            _sortFiltersList.value = sortFilters
        }
    }

    fun modifyFavorite(idRestaurant: Long){

    }

}