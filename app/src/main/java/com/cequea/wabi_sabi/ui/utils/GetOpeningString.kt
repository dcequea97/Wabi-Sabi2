package com.cequea.wabi_sabi.ui.utils

import java.time.LocalTime

fun getOpeningString(open: LocalTime, close: LocalTime): String {
    return "Abierto entre las ${open.formatToTwoDigits()} y las ${close.formatToTwoDigits()}"
}

fun getProductsHours(open: LocalTime, close: LocalTime): String {
    return "Disponible entre las ${open.formatToTwoDigits()} y las ${close.formatToTwoDigits()}"
}

fun LocalTime.formatToTwoDigits(): String {
    val hourFormatted = if (this.hour.toString().length < 2) {
        "0${this.hour}"
    } else {
        this.hour.toString()
    }
    val minuteFormatted = if (this.minute.toString().length < 2) {
        "0${this.minute}"
    } else {
        this.minute.toString()
    }
    return "$hourFormatted:$minuteFormatted"
}