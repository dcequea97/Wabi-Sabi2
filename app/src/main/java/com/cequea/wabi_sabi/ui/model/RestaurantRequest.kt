package com.cequea.wabi_sabi.ui.model

import java.time.DayOfWeek
import java.time.LocalTime

data class RestaurantRequest(
    val id: Long,
    val name: String,
    val tagline: String,
    val backgroundImageUrl: String,
    val profileImageUrl: String,
    val rating: Double,
    val openingHours: LocalTime,
    val closingHours: LocalTime,
    val workingDays: Set<DayOfWeek>,
    val isFavorite: Boolean,
    val isHighlight: Boolean,
    val status: Boolean = false,
    val idUserRequested: Int
)