package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.responses.DollarPrice
import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.DollarApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DollarService @Inject constructor(
    private val api: DollarApiClient,
    private val safeApiCall: SafeApiCall
) {

    suspend fun getDollarPrice(): DollarPrice? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getDollarPrice() }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body
            }
        }
    }

}