package com.cequea.wabi_sabi.data.repository

import android.content.Context
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.data.model.responses.OrderResponse
import com.cequea.wabi_sabi.data.network.services.RestaurantRequestService
import com.cequea.wabi_sabi.ui.model.RestaurantRequest
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class RestaurantRequestRepository @Inject constructor(
    private val api: RestaurantRequestService,
    @ApplicationContext private val context: Context
) {


    suspend fun getAllRestaurantRequests(): Resource<List<RestaurantRequest>> {
        val response = api.getAllRestaurantRequests()
        if (response.isNull()) {
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response
        )
    }

    suspend fun saveRestaurantRequest(
        restaurantRequestId: Long
    ): Resource<RestaurantRequest> {
        val response = api.saveRestaurantRequest(restaurantRequestId)
        if (response.isNullOrEmpty()) {
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response!!
        )
    }

}


