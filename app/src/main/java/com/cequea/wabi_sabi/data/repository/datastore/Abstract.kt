package com.cequea.wabi_sabi.data.repository.datastore

import kotlinx.coroutines.flow.Flow

interface Abstract {
    suspend fun saveUser(user: UserDataStore)

    suspend fun getUser():Flow<UserDataStore>

    suspend fun saveToken(newToken: String)

    suspend fun getToken():Flow<String>

    suspend fun saveAddress(newAddress: String)

    suspend fun  getAddress(): Flow<String>
}