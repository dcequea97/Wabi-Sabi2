package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.AddressModel
import com.cequea.wabi_sabi.data.model.AddressResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AddressApiClient {

    @POST("addresses")
    suspend fun addAddress(
        @Body address: JsonObject
    ): Response<AddressModel>

    @GET("addresses/user/{idUser}")
    suspend fun getAddressByUser(@Path("idUser") idUser: Long): Response<AddressResponse>

}