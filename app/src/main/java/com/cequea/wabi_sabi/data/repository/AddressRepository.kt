package com.cequea.wabi_sabi.data.repository

import android.content.Context
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNotNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.data.network.services.AddressService
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.Address
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AddressRepository @Inject constructor(
    private val api: AddressService,
    private val dataStore: DataStoreRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun getDefaultAddress(idUser: Long): Resource<Address> {
        val response = api.getAddressByUser(idUser)
        return if (response.isNullOrEmpty()){
            Resource.Error(
                message = context.getString(R.string.universal_error),
                data = null
            )
        }else{
            Resource.Success(
                data = response.first {
                    it.default
                }
            )

        }
    }

    suspend fun addAddress(address: Address, idUser: Long): Resource<Address> {
        val response = api.addAddress(address, idUser)
        return if (response.isNullOrEmpty()){
            Resource.Error(
                message = context.getString(R.string.universal_error),
                data = null
            )
        }else{
            saveAddress(response?.addressName)
            Resource.Success(
                data = response!!
            )

        }
    }

    private suspend fun saveAddress(defaultAddress: String?) {
        if (defaultAddress.isNotNull()) {
            dataStore.saveAddress(defaultAddress!!)
        }
    }

}


