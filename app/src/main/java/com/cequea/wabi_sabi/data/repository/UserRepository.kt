package com.cequea.wabi_sabi.data.repository

import android.content.Context
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNotNull
import com.cequea.wabi_sabi.core.isNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.data.model.responses.LoginResponse
import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.ui.model.User
import com.cequea.wabi_sabi.data.network.services.UserService
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.data.repository.datastore.toDataStore
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val api: UserService,
    private val dataStore: DataStoreRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun getUserByEmail(email: String): Resource<User> {
        val response = api.getUserByEmail(email)
        if (response.isNull()){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response!!
        )
    }

    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val response = api.login(email, password)
        saveToken(response?.token)
        saveUser(response?.data?.toDomain())
        return if (response.isNullOrEmpty()){
            Resource.Error(
                message = "Ha ocurrido un error",
                data = null
            )
        }else{
            Resource.Success(
                data = response!!
            )

        }
    }

    private suspend fun saveToken(token: String?) {
        if (token.isNotNull()) {
            dataStore.saveToken(token!!)
        }
    }
    private suspend fun saveUser(user: User?) {
        if (user.isNotNull()) {
            dataStore.saveUser(user!!.toDataStore())
        }
    }

}


