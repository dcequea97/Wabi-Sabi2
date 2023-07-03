package com.cequea.wabi_sabi.data.repository

import android.content.Context
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.data.model.CartResponse
import com.cequea.wabi_sabi.data.network.services.ProductService
import com.cequea.wabi_sabi.data.network.services.RegisterBusinessService
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.Product
import com.cequea.wabi_sabi.ui.model.RegisterBusiness
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject


class RegisterBusinessRepository @Inject constructor(
    private val api: RegisterBusinessService,
    @ApplicationContext private val context: Context
) {
    suspend fun registerBusiness(business: RegisterBusiness, idUser: Long): Resource<RegisterBusiness>{
            val response = api.registerBusiness(business, idUser)
            if (response.isNull()){
                return Resource.Error(
                    message = context.getString(R.string.universal_error)
                )
            }
            return Resource.Success(
                response!!
            )
    }


}


