package com.cequea.wabi_sabi.ui.model

data class Address(
    val id: Long?,
    val addressName: String,
    val addressDetail: String,
    val state: String,
    val city: String,
    val referencePoint: String,
    val default: Boolean = false
)
