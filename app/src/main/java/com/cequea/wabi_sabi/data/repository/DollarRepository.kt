package com.cequea.wabi_sabi.data.repository

import android.content.Context
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNotNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.data.model.responses.DollarPrice
import com.cequea.wabi_sabi.data.network.services.AddressService
import com.cequea.wabi_sabi.data.network.services.DollarService
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.Address
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class DollarRepository @Inject constructor(
    private val api: DollarService,
    @ApplicationContext private val context: Context
) {

    suspend fun getDollarPrice(): Resource<Double> {
        val response = api.getDollarPrice()
        return if (response.isNullOrEmpty()){
            Resource.Error(
                message = context.getString(R.string.universal_error),
                data = null
            )
        }else{
            Resource.Success(
                data = response!!.price
            )

        }
    }
}


