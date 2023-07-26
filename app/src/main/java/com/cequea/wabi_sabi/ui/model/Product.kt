package com.cequea.wabi_sabi.ui.model

import androidx.compose.runtime.Immutable
import java.time.DayOfWeek
import java.time.LocalTime

@Immutable
data class Product(
    val id: Long,
    val restaurantId: Long,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val description: String = "",
    val categoryId: List<Long>,
    var countInCart: Int = 0,
    val openingHours: LocalTime,
    val closingHours: LocalTime,
    val openingDays: Set<DayOfWeek>,
    val productsQuantity: Int,
    val quantity: Int,
    val quantityFreeze: Int,
    val status: Boolean
)