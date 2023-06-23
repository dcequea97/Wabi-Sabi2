package com.cequea.wabi_sabi.data.repository

import com.cequea.wabi_sabi.core.isNotNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.ui.model.Address
import com.cequea.wabi_sabi.data.model.responses.LoginResponse
import com.cequea.wabi_sabi.data.model.responses.toDomain
import com.cequea.wabi_sabi.ui.model.User
import com.cequea.wabi_sabi.data.network.services.UserService
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.data.repository.datastore.toDataStore
import com.cequea.wabi_sabi.util.Resource
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val api: UserService,
    private val dataStore: DataStoreRepository
) {

    suspend fun getUser(): Resource<User> {
        return Resource.Success(
            user
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

    private val user = User(
        1,
        2,
        "David Cequea",
        "data:image/png;base64,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",
        "dcequea97@gmail.com",
        "4241854419",
        listOf(
            Address(
                "Home",
                "Mi casa",
                true
            )
        )
    )

}


