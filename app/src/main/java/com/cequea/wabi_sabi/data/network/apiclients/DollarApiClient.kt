package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.responses.DollarPrice
import retrofit2.Response
import retrofit2.http.GET

interface DollarApiClient {

    @GET("getDollarPrice")
    suspend fun getDollarPrice(): Response<DollarPrice>

}