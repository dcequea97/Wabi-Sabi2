package com.cequea.wabi_sabi.ui.model

data class RegisterBusiness(
    val id: Long?,
    val name: String,
    val backgroundImageUrl: String,
    val profileImageUrl: String,
    val tagline: String,
    val workingDays: List<Int>,
    val openingHours: String,
    val closingHours: String
)