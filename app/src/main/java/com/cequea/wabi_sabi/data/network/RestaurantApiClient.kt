package com.cequea.wabi_sabi.data.network

import com.cequea.wabi_sabi.data.model.Restaurant
import retrofit2.Response
import retrofit2.http.GET

interface RestaurantApiClient {

    @GET("restaurants")
    suspend fun getAllRestaurants(): Response<Restaurant>

}