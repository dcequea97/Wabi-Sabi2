package com.cequea.wabi_sabi.data.model

import com.cequea.wabi_sabi.core.toDayOfWeek
import com.cequea.wabi_sabi.ui.model.Product
import com.google.gson.annotations.SerializedName
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ProductsResponse : ArrayList<ProductModel>()

data class ProductModel(
    @SerializedName("id") val id: Long,
    @SerializedName("restaurant_id") val restaurantId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String = "",
    @SerializedName("category_id") val categoryId: ArrayList<Long>,
    @SerializedName("count_in_cart") var countInCart: Int = 0,
    @SerializedName("opening_hours")  private val openingHoursStr: String?,
    @SerializedName("closing_hours") private val closingHoursStr: String?,
    @SerializedName("opening_days") val openingDays: ArrayList<Int>
){
    val openingHours: LocalTime
        get() = openingHoursStr?.let {
            LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME)
        } ?: LocalTime.MIN

    val closingHours: LocalTime
        get() = closingHoursStr?.let {
            LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME)
        } ?: LocalTime.MAX
}

fun ProductModel.toDomain() = Product(
    id = id,
    restaurantId = restaurantId,
    name = name,
    imageUrl = imageUrl,
    price = price,
    description = description,
    categoryId = categoryId,
    countInCart = countInCart,
    openingHours = openingHours,
    closingHours = closingHours,
    openingDays = openingDays.toDayOfWeek()
)