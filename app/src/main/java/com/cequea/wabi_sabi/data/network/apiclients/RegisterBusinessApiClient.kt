package com.cequea.wabi_sabi.data.network.apiclients


import com.cequea.wabi_sabi.data.model.responses.RegisterBusinessResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterBusinessApiClient {

    @POST("restaurants_request")
    suspend fun registerBusiness(
        @Body restaurantRequestData: JsonObject
    ): Response<RegisterBusinessResponse>

}