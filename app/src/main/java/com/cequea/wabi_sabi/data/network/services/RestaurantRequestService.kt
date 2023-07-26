package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.responses.RestaurantRequestResponseItem
import com.cequea.wabi_sabi.data.model.responses.toDomain
import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.RestaurantRequestApiClient
import com.cequea.wabi_sabi.ui.model.RestaurantRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class RestaurantRequestService @Inject constructor(
    private val api: RestaurantRequestApiClient,
    private val safeApiCall: SafeApiCall
) {

    suspend fun getAllRestaurantRequests(): List<RestaurantRequest> {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getAllRestaurantRequests() }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                emptyList()
            } else {
                apiCall.body.map {
                    it.toDomain()
                }
            }
        }
    }

    suspend fun saveRestaurantRequest(
        restaurantRequestId: Long
    ): RestaurantRequest? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                jsonObject.addProperty("restaurant_request_id", restaurantRequestId)
                api.saveRestaurantRequest(jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                 apiCall.body.toDomain()
            }
        }
    }

}
