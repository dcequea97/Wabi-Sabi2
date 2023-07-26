package com.cequea.wabi_sabi.data.model.responses
import com.cequea.wabi_sabi.core.toDayOfWeek
import com.cequea.wabi_sabi.ui.model.RestaurantRequest
import com.google.gson.annotations.SerializedName
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class RestaurantRequestResponse : ArrayList<RestaurantRequestResponseItem>()

data class RestaurantRequestResponseItem(
    @SerializedName("background_image_url") val backgroundImageUrl: String,
    @SerializedName("closing_hours") val closingHoursStr: String?,
    @SerializedName("id") val id: Long,
    @SerializedName("id_user_requested") val idUserRequested: Int,
    @SerializedName("is_favorite") val isFavorite: Boolean,
    @SerializedName("is_highlight") val isHighlight: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("opening_hours") val openingHoursStr: String?,
    @SerializedName("profile_image_url") val profileImageUrl: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("status") val status: Boolean,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("working_days") val workingDays: ArrayList<Int>
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

fun RestaurantRequestResponseItem.toDomain(): RestaurantRequest {
    return RestaurantRequest(
        id = id,
        name = name,
        tagline = tagline,
        backgroundImageUrl = backgroundImageUrl,
        profileImageUrl = profileImageUrl,
        rating = rating,
        openingHours = openingHours,
        closingHours = closingHours,
        workingDays = workingDays.toDayOfWeek(),
        isFavorite = isFavorite,
        isHighlight = isHighlight,
        status = status,
        idUserRequested = idUserRequested
    )
}

