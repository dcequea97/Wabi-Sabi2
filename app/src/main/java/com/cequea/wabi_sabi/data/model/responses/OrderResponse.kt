package com.cequea.wabi_sabi.data.model.responses

import com.cequea.wabi_sabi.ui.model.Order
import com.cequea.wabi_sabi.ui.model.OrderProduct
import com.cequea.wabi_sabi.ui.model.OrderStatus
import com.google.gson.annotations.SerializedName

class OrderListResponse : ArrayList<OrderResponse>()

data class OrderResponse(
    @SerializedName("address") val address: String,
    @SerializedName("contact_number") val contactNumber: String,
    @SerializedName("date_time") val dateTime: String,
    @SerializedName("id") val id: Int,
    @SerializedName("payment_method") val paymentMethod: String,
    @SerializedName("products") val products: List<OrderResponseProduct>,
    @SerializedName("restaurant_id") val restaurantId: Int,
    @SerializedName("status") val status: OrderResponseStatus,
    @SerializedName("total_price") val totalPrice: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("bank") val bank: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("reference_number") val referenceNumber: String
)

fun OrderResponse.toDomain() = Order(
    id = id,
    restaurantId = restaurantId,
    userId = userId,
    dateTime = dateTime,
    totalPrice = totalPrice,
    status = status.toDomain(),
    paymentMethod = paymentMethod,
    contactNumber = contactNumber,
    address = address,
    products = products.map{ it.toDomain() },
    bank = bank,
    phoneNumber = phoneNumber,
    referenceNumber =  referenceNumber
)

data class OrderResponseProduct(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("product_image_url") val productImageUrl: String,
    @SerializedName("product_name") val productName: String,
    @SerializedName("price") val price: Double
)

fun OrderResponseProduct.toDomain() = OrderProduct(
    productId = productId,
    quantity = quantity,
    productImageUrl = productImageUrl,
    productName = productName,
    price = price
)

data class OrderResponseStatus(
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int
)

fun OrderResponseStatus.toDomain() = OrderStatus(
    description = description,
    id = id
)