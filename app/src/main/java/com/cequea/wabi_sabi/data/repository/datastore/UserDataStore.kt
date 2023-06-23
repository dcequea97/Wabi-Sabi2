package com.cequea.wabi_sabi.data.repository.datastore

import com.cequea.wabi_sabi.ui.model.User

data class UserDataStore(
    val id: Long,
    val idProfile: Long,
    val name: String,
    val imageUrl: String,
    val email: String,
    val phone: String
)

fun User.toDataStore(): UserDataStore = UserDataStore(
    id = id,
    idProfile = idProfile,
    name = name,
    imageUrl= imageUrl,
    email = email,
    phone = phone
)