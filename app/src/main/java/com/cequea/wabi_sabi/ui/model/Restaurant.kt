package com.cequea.wabi_sabi.ui.model

import androidx.compose.runtime.Immutable
import java.time.DayOfWeek
import java.time.LocalTime

@Immutable
data class Restaurant(
    val id: Long,
    val name: String,
    val backgroundImageUrl: String,
    val profileImageUrl: String,
    val tagline: String = "",
    val workingDays: Set<DayOfWeek>,
    val rating: Double = 4.5,
    val openingHours: LocalTime,
    val closingHours: LocalTime,
    val isFavorite: Boolean = false,
    val isHighlight: Boolean = false,
    val status: Boolean
)

data class RestaurantsCollection(
    val restaurants: List<Restaurant>,
    val label: String,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, Highlight }