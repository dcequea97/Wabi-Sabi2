package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.AddressApiClient
import com.cequea.wabi_sabi.ui.model.Address
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressService @Inject constructor(
    private val api: AddressApiClient,
    private val safeApiCall: SafeApiCall
) {

    suspend fun addAddress(address: Address, idUser: Long): Address?{
        return withContext(Dispatchers.IO){
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                jsonObject.addProperty("address_name", address.addressName)
                jsonObject.addProperty("state", address.state)
                jsonObject.addProperty("city", address.city)
                jsonObject.addProperty("address_detail", address.addressDetail)
                jsonObject.addProperty("reference_point", address.referencePoint)
                jsonObject.addProperty("default", address.default)
                jsonObject.addProperty("user_id", idUser)
                api.addAddress(jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful){
                null
            }else{
                apiCall.body.toDomain()
            }
        }
    }

    suspend fun getAddressByUser(idUser: Long): List<Address> {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getAddressByUser(idUser) }
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