package com.cequea.wabi_sabi.core

import java.time.DayOfWeek

fun Any?.isNull() = this == null

fun Any?.isNullOrEmpty() = this == null || this == ""

fun Any?.isNotNull() = this != null

fun ArrayList<Int>.toDayOfWeek(): Set<DayOfWeek> {
    return this.map { day ->
        when (day) {
            1 -> DayOfWeek.MONDAY
            2 -> DayOfWeek.TUESDAY
            3 -> DayOfWeek.WEDNESDAY
            4 -> DayOfWeek.THURSDAY
            5 -> DayOfWeek.FRIDAY
            6 -> DayOfWeek.SATURDAY
            7 -> DayOfWeek.SUNDAY
            else -> throw IllegalArgumentException("Invalid day of week: $day")
        }
    }.toSet()
}