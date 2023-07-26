package com.cequea.wabi_sabi.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cequea.wabi_sabi.ui.model.Order

@Composable
fun TwoButtonsRow(
    onDeclineClick: (Int) -> Unit,
    onAcceptClick: (Int) -> Unit,
    order: Order,
    textFirstButton: String?,
    textSecondButton: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        textFirstButton?.let {
            WabiSabiButton(onClick = { onDeclineClick(order.id) }) {
                Text(text = textFirstButton)
            }
            Spacer(modifier = Modifier.width(15.dp))
        }
        WabiSabiButton(onClick = { onAcceptClick(order.id) }) {
            Text(text = textSecondButton)
        }

    }
}