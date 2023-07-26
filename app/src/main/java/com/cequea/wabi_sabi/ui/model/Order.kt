package com.cequea.wabi_sabi.ui.model

data class Order(
    val id: Int,
    val restaurantId: Int,
    val userId: Int,
    val dateTime: String,
    val totalPrice: String,
    val status: OrderStatus,
    val paymentMethod: String?,
    val contactNumber: String,
    val address: String,
    val products: List<OrderProduct>,
    val bank: String,
    val referenceNumber: String,
    val phoneNumber: String
)

data class OrderProduct(
    val productId: Int,
    val productName: String,
    val productImageUrl: String,
    val quantity: Int,
    val price: Double
)

data class OrderStatus(
    val id: Int,
    val description: String,
    val order: Int
)