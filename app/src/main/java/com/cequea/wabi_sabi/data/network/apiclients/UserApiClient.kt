package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.responses.LoginResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiClient {

    @POST("register")
    suspend fun registerUser(@Body user: JsonObject): Response<LoginResponse>

    @POST("login")
    suspend fun login(@Body user: JsonObject): Response<LoginResponse>
}