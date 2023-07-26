package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.RestaurantApiClient
import com.cequea.wabi_sabi.ui.model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class RestaurantService @Inject constructor(
    private val api: RestaurantApiClient,
    private val safeApiCall: SafeApiCall
) {

    suspend fun getAllRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getAllRestaurants() }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                emptyList()
            } else {
                apiCall.body.map {
                    it.toDomain()
                }
            }
        }
    }

    suspend fun getRestaurantById(idRestaurant: Long): Restaurant? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getRestaurantById(idRestaurant) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body.toDomain()
            }
        }
    }

    suspend fun getRestaurantByIdUser(idUser: Long): Restaurant? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getRestaurantByIdUser(idUser) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body.toDomain()
            }
        }
    }
}
