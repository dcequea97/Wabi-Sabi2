package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.responses.LoginResponse
import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.UserApiClient
import com.cequea.wabi_sabi.ui.model.User
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(
    private val api: UserApiClient,
    private val safeApiCall: SafeApiCall
) {

    suspend fun registerUser(email: String, name: String, password: String, userProfile: Int): LoginResponse?{
        return withContext(Dispatchers.IO){
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                jsonObject.addProperty("email", email)
                jsonObject.addProperty("name", name)
                jsonObject.addProperty("id_profile", userProfile.toString())
                jsonObject.addProperty("password", password)
                jsonObject.addProperty("password_confirmation", password)
                api.registerUser(jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful){
                null
            }else{
                apiCall.body
            }
        }
    }

    suspend fun login(email: String, password: String): LoginResponse? {
        return withContext(Dispatchers.IO){
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                jsonObject.addProperty("email", email)
                jsonObject.addProperty("password", password)
                api.login(jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful){
                null
            }else if (apiCall.statusCode == 401){
                null
            }else{
                apiCall.body
            }
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getUserByEmail(email) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body.user.toDomain()
            }
        }
    }


}