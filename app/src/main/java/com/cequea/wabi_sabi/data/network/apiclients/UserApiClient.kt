package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.UserModel
import com.cequea.wabi_sabi.data.model.UserResponse
import com.cequea.wabi_sabi.data.model.responses.LoginResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiClient {

    @POST("register")
    suspend fun registerUser(@Body user: JsonObject): Response<LoginResponse>

    @POST("login")
    suspend fun login(@Body user: JsonObject): Response<LoginResponse>

    @GET("getUserByEmail/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserResponse>
}