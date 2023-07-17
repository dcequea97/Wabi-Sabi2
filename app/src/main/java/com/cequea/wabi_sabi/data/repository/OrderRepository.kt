package com.cequea.wabi_sabi.data.repository

import android.content.Context
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.data.model.responses.OrderResponse
import com.cequea.wabi_sabi.data.network.services.OrderService
import com.cequea.wabi_sabi.ui.model.Order
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class OrderRepository @Inject constructor(
    private val api: OrderService,
    @ApplicationContext private val context: Context
) {
    suspend fun saveOrder(
        idUser: Long,
        bank: String,
        phoneNumber: String,
        referenceNumber: String
    ): Resource<OrderResponse> {
        val response = api.saveOrder(idUser, bank, phoneNumber, referenceNumber)
        if (response.isNullOrEmpty()) {
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response!!
        )
    }

    suspend fun getOrdersByUser(idUser: Long): Resource<List<Order>> {
        val response = api.getOrdersByUser(idUser)
        if (response.isNull()) {
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response
        )
    }

}


