package com.cequea.wabi_sabi.data.model

import com.cequea.wabi_sabi.core.toDayOfWeek
import com.cequea.wabi_sabi.ui.model.Restaurant
import com.google.gson.annotations.SerializedName
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class RestaurantResponse : ArrayList<RestaurantModel>()

data class RestaurantModel(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("background_image_url") val backgroundImageUrl: String,
    @SerializedName("profile_image_url") val profileImageUrl: String,
    @SerializedName("tagline") val tagline: String = "",
    @SerializedName("working_days") val workingDays: ArrayList<Int>,
    @SerializedName("rating") val rating: Double = 4.5,
    @SerializedName("opening_hours") private val openingHoursStr: String?,
    @SerializedName("closing_hours") private val closingHoursStr: String?,
    @SerializedName("is_favorite") val isFavorite: Boolean = false,
    @SerializedName("is_highlight") val isHighlight: Boolean = false
) {
    val openingHours: LocalTime
        get() = openingHoursStr?.let {
            LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME)
        } ?: LocalTime.MIN

    val closingHours: LocalTime
        get() = closingHoursStr?.let {
            LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME)
        } ?: LocalTime.MAX
}

fun RestaurantModel.toDomain(): Restaurant {
    return Restaurant(
        id = id,
        name = name,
        backgroundImageUrl = backgroundImageUrl,
        profileImageUrl = profileImageUrl,
        tagline = tagline,
        workingDays = workingDays.toDayOfWeek(),
        rating = rating,
        openingHours = openingHours,
        closingHours = closingHours,
        isFavorite = isFavorite,
        isHighlight = isHighlight
    )
}