package com.cequea.wabi_sabi.data.model.responses

import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.ui.model.Address
import com.cequea.wabi_sabi.ui.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val data: UserModel,
    val token: String
)

data class UserModel(
    @SerializedName("id") val id: Long,
    @SerializedName("id_profile") val idProfile: Long,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("addresses") val addresses: List<AddressModel>  = emptyList()
)

data class AddressModel(
    @SerializedName("address_name") val addressName: String,
    @SerializedName("address_description") val addressDescription: String,
    @SerializedName("default") val default: Boolean = false
)

fun AddressModel.toDomain(): Address = Address(
    addressName = addressName,
    addressDescription = addressDescription,
    default = default
)

fun UserModel.toDomain(): User = User(
    id= id,
    idProfile = idProfile,
    name = name,
    imageUrl = imageUrl,
    email = email,
    phone = phone,
    addresses = if(!addresses.isNullOrEmpty()){
        addresses.map { it.toDomain() }
    } else {
        emptyList()
    }
)