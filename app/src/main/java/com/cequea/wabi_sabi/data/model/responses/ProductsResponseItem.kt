package com.cequea.wabi_sabi.data.model.responses

data class ProductsResponseItem(
    val category_id: List<Int>,
    val closing_hours: String,
    val count_in_cart: Int,
    val description: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val opening_days: List<Int>,
    val opening_hours: String,
    val price: String,
    val restaurant_id: Int
)