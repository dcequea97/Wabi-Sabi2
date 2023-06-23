package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.RestaurantModel
import com.cequea.wabi_sabi.data.model.RestaurantResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantApiClient {

    @GET("restaurants")
    suspend fun getAllRestaurants(): Response<RestaurantResponse>

    @GET("restaurants/{idRestaurant}")
    suspend fun getRestaurantById(@Path("idRestaurant") idRestaurant: Long): Response<RestaurantModel>

}