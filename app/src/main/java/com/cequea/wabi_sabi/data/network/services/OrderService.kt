package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.responses.OrderResponse
import com.cequea.wabi_sabi.data.model.responses.toDomain
import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.OrderApiClient
import com.cequea.wabi_sabi.ui.model.Order
import com.cequea.wabi_sabi.ui.model.Product
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class OrderService @Inject constructor(
    private val api: OrderApiClient,
    private val safeApiCall: SafeApiCall
) {

    suspend fun saveOrder(idUser: Long, paymentMethod: String = "WhatsApp"): OrderResponse? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                jsonObject.addProperty("user_id", idUser)
                jsonObject.addProperty("payment_method", paymentMethod)
                api.saveOrder(jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body
            }
        }
    }

    suspend fun getOrdersByUser(idUser: Long): List<Order> {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getOrdersByUser(idUser) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                emptyList()
            } else {
                apiCall.body.map {
                    it.toDomain()
                }
            }
        }
    }

}
