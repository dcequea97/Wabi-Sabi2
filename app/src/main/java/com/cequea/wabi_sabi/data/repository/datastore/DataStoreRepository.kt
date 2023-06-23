package com.cequea.wabi_sabi.data.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val DATASTORE_NAME = "DATA_STORE"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreRepository(private val context: Context) : Abstract {

    companion object {
        val idUser = longPreferencesKey("ID_USER")
        val idProfile = longPreferencesKey("ID_PROFILE")
        val name = stringPreferencesKey("NAME")
        val email = stringPreferencesKey("EMAIL")
        val phone = stringPreferencesKey("PHONE")
        val imageUrl = stringPreferencesKey("IMAGE_URL")

        val token = stringPreferencesKey("TOKEN")
    }

    override suspend fun saveUser(user: UserDataStore) {
        context.datastore.edit { users ->
            users[idUser] = user.id
            users[idProfile] = user.idProfile
            users[name] = user.name
            users[email] = user.email
            users[phone] = user.phone
            users[imageUrl] =  user.imageUrl
        }
    }

    override suspend fun getUser() = context.datastore.data.map { user ->
        UserDataStore(
            id = user[idUser]!!,
            idProfile = user[idProfile]!!,
            name = user[name]!!,
            email = user[email]!!,
            phone = user[phone]!!,
            imageUrl = user[imageUrl]!!
        )
    }

    override suspend fun saveToken(newToken: String) {
        context.datastore.edit {
            it[token] = newToken
        }
    }

    override suspend fun getToken(): Flow<String> {
        return context.datastore.data.map {
            it[token]!!
        }
    }
}