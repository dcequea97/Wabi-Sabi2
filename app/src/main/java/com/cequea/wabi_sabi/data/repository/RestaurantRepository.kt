package com.cequea.wabi_sabi.data.repository

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.ui.model.Filter
import com.cequea.wabi_sabi.ui.model.Restaurant
import com.cequea.wabi_sabi.ui.model.RestaurantsCollection
import com.cequea.wabi_sabi.data.network.services.RestaurantService
import com.cequea.wabi_sabi.ui.model.CollectionType
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val api: RestaurantService,
    @ApplicationContext private val context: Context
) {

    suspend fun getRestaurants(): Resource<List<RestaurantsCollection>> {
        val response = api.getAllRestaurants()
        if (response.isNullOrEmpty()) {
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            listOf(
                RestaurantsCollection(
                    restaurants = response.filter {
                        it.isHighlight
                    },
                    label = "Destacados",
                    type = CollectionType.Highlight
                ),
                RestaurantsCollection(
                    restaurants = response,
                    label = "Descubre",
                    type = CollectionType.Normal
                )
            )
        )
    }

    suspend fun getRestaurantById(idRestaurant: Long): Resource<Restaurant> {
        val response = api.getRestaurantById(idRestaurant)
        if (response.isNull()){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response!!
        )

    }

    suspend fun getFilters(): List<Filter> {
        return listOf(
            Filter(name = "Organic"),
            Filter(name = "Gluten-free"),
            Filter(name = "Dairy-free"),
            Filter(name = "Sweet"),
            Filter(name = "Savory")
        )
    }

    suspend fun getSortFilters(): List<Filter> {
        return listOf(
            Filter(name = "People Favorite", icon = Icons.Filled.People),
            Filter(name = "Rating", icon = Icons.Filled.Star),
            Filter(name = "Alphabetical", icon = Icons.Filled.SortByAlpha)
        )
    }

    suspend fun getPriceFilters(): List<Filter> {
        return listOf(
            Filter(name = "$"),
            Filter(name = "$$"),
            Filter(name = "$$$"),
            Filter(name = "$$$$")
        )
    }

    suspend fun getCategoryFilters(): List<Filter> {
        return listOf(
            Filter(name = "Chips & crackers"),
            Filter(name = "Fruit snacks"),
            Filter(name = "Desserts"),
            Filter(name = "Nuts")
        )
    }

    suspend fun getLifeStyleFilters(): List<Filter> {
        return listOf(
            Filter(name = "Organic"),
            Filter(name = "Gluten-free"),
            Filter(name = "Dairy-free"),
            Filter(name = "Sweet"),
            Filter(name = "Savory")
        )
    }
}