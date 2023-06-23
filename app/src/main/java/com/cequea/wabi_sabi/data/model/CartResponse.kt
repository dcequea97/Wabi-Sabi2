package com.cequea.wabi_sabi.data.model

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("count_in_cart") val countInCart: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("user_id") val userId: Int
)