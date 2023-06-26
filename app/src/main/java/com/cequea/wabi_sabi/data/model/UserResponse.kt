package com.cequea.wabi_sabi.data.model

import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.ui.model.Address
import com.cequea.wabi_sabi.ui.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user") val user: UserModel
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

class AddressResponse: ArrayList<AddressModel>()

data class AddressModel(
    @SerializedName("id") val id: Long,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("address_detail") val addressDetail: String,
    @SerializedName("state") val state: String,
    @SerializedName("city") val city: String,
    @SerializedName("reference_point") val referencePoint: String,
    @SerializedName("is_default") val isDefault: Boolean = false
)

fun AddressModel.toDomain(): Address = Address(
    id = id,
    addressName = addressName,
    addressDetail = addressDetail,
    state = state,
    city = city,
    referencePoint = referencePoint,
    default = isDefault
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