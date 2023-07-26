 package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.responses.RestaurantRequestResponse
import com.cequea.wabi_sabi.data.model.responses.RestaurantRequestResponseItem
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

 interface RestaurantRequestApiClient {

     @GET("restaurant_requests")
     suspend fun getAllRestaurantRequests(): Response<RestaurantRequestResponse>

     @POST("restaurants/by_restaurant_request")
     suspend fun saveRestaurantRequest(@Body requestData: JsonObject): Response<RestaurantRequestResponseItem>


}