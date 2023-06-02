package com.cequea.wabi_sabi.ui.utils

import java.math.BigDecimal
import java.text.NumberFormat

fun formatPrice(price: Double): String {
    return NumberFormat.getCurrencyInstance().format(
        BigDecimal(price)
    )
}