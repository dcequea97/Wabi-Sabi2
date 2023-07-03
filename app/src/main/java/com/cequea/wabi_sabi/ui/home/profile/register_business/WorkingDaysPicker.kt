package com.cequea.wabi_sabi.ui.home.profile.register_business

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun WorkingDaysPicker(
    workingDaysValue: MutableState<List<Int>>,
    onWorkingDaysChanged: (List<Int>) -> Unit
) {
    Column {
        Text(text = "Dias de Trabajo")

        Row {
            CheckBoxDays(workingDaysValue, onWorkingDaysChanged, Calendar.MONDAY, "Lun")

            CheckBoxDays(workingDaysValue, onWorkingDaysChanged, Calendar.TUESDAY, "Mar")

            CheckBoxDays(workingDaysValue, onWorkingDaysChanged, Calendar.WEDNESDAY, "Mie")

            CheckBoxDays(workingDaysValue, onWorkingDaysChanged, Calendar.THURSDAY, "Jue")

            CheckBoxDays(workingDaysValue, onWorkingDaysChanged, Calendar.FRIDAY, "Vie")

            CheckBoxDays(workingDaysValue, onWorkingDaysChanged, Calendar.SATURDAY, "Sab")

            CheckBoxDays(workingDaysValue, onWorkingDaysChanged, Calendar.SUNDAY, "Dom")
        }
    }
}

@Composable
private fun CheckBoxDays(
    workingDaysValue: MutableState<List<Int>>,
    onWorkingDaysChanged: (List<Int>) -> Unit,
    calendarDay: Int,
    dayString: String
) {
    Column(

    ) {
        Text(text = dayString, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
        Checkbox(
            checked = workingDaysValue.value.contains(calendarDay),
            onCheckedChange = { checked ->
                val newWorkingDays = if (checked) {
                    workingDaysValue.value + calendarDay
                } else {
                    workingDaysValue.value - calendarDay
                }
                onWorkingDaysChanged(newWorkingDays)
            },
            modifier = Modifier.weight(1f)
        )
    }
}