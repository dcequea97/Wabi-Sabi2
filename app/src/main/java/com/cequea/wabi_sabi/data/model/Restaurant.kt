package com.cequea.wabi_sabi.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Restaurant(
    val id: Long,
    val name: String,
    val backgroundImageUrl: String,
    val profileImageUrl: String,
    val tagline: String = "",
    val openingHour: Long,
    val closeHour: Long,
    val workingDays: ArrayList<Int>,
    val rating: Double = 4.5,
    val pickupFirstTime: Int = 45,
    val pickupSecondTime: Int = 60,
)

data class RestaurantsCollection(
    val restaurants: List<Restaurant>,
    val label: String,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, Highlight }