package com.cequea.wabi_sabi.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val id: Long,
    val restaurantId: Long,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val description: String = "",
    val categoryId: List<Long>,
    val countInCart: Int = 0
)