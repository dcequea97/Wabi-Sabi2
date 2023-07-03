package com.cequea.wabi_sabi.data.model.responses

import com.cequea.wabi_sabi.ui.model.RegisterBusiness
import com.google.gson.annotations.SerializedName

data class RegisterBusinessResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("background_image_url") val backgroundImageUrl: String,
    @SerializedName("closing_hours") val closingHours: String,
    @SerializedName("id_user_requested") val idUserRequested: Int,
    @SerializedName("name") val name: String,
    @SerializedName("opening_hours") val openingHours: String,
    @SerializedName("profile_image_url") val profileImageUrl: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("working_days") val workingDays: List<Int>
)

fun RegisterBusinessResponse.toDomain() = RegisterBusiness(
    id = id,
    name = name,
    backgroundImageUrl = backgroundImageUrl,
    profileImageUrl = profileImageUrl,
    tagline = tagline,
    workingDays = workingDays,
    openingHours = openingHours,
    closingHours = closingHours
)