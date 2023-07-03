package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.responses.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.RegisterBusinessApiClient
import com.cequea.wabi_sabi.ui.model.RegisterBusiness
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class RegisterBusinessService @Inject constructor(
    private val api: RegisterBusinessApiClient,
    private val safeApiCall: SafeApiCall
) {


    suspend fun registerBusiness(business: RegisterBusiness, idUser: Long): RegisterBusiness? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                val jsonArray = JsonArray()
                business.workingDays.forEach {
                    jsonArray.add(it)
                }
                jsonObject.addProperty("id_user_requested", idUser)
                jsonObject.addProperty("name", business.name)
                jsonObject.addProperty("background_image_url", business.backgroundImageUrl)
                jsonObject.addProperty("profile_image_url", business.profileImageUrl)
                jsonObject.addProperty("tagline", business.tagline)
                jsonObject.add("working_days", jsonArray)
                jsonObject.addProperty("opening_hours", business.openingHours)
                jsonObject.addProperty("closing_hours", business.closingHours)

                api.registerBusiness(jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body.toDomain()
            }
        }
    }


}
