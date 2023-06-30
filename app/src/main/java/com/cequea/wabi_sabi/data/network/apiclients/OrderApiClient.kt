 package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.responses.OrderListResponse
import com.cequea.wabi_sabi.data.model.responses.OrderResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

 interface OrderApiClient {

    @POST("orders")
    suspend fun saveOrder(
        @Body orderData: JsonObject
    ): Response<OrderResponse>

    @GET("orders/user/{idUser}")
    suspend fun getOrdersByUser(@Path("idUser") idUser: Long): Response<OrderListResponse>

}