package com.cequea.wabi_sabi.ui.model

data class User(
    val id: Long,
    val idProfile: Long,
    val name: String,
    val imageUrl: String,
    val email: String,
    val phone: String,
    val addresses: List<Address> = emptyList()
)


