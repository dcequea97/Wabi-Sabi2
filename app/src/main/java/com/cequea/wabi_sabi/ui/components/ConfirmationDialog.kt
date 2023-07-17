package com.cequea.wabi_sabi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    onConfirm: (bank: String, phoneNumber: String, referenceNumber: String) -> Unit,
    onCancel: () -> Unit,
    price: Double,
    bankValue: MutableState<String>,
    phoneValue: MutableState<String>,
    referenceValue: MutableState<String>
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface(
            modifier = Modifier.width(280.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onPrimary
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Para proceder al pago realice un pago móvil por el monto de $price, a los siguientes datos:",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Banco: Banco Mercantil",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cédula: V26774371",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Teléfono: 04142240769",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )

                val focusManager = LocalFocusManager.current

                TransparentTextField(
                    textFieldValue = bankValue,
                    textLabel = "Banco",
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = phoneValue,
                    textLabel = "Telefono Pago Movil",
                    keyboardType = KeyboardType.Phone,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = referenceValue,
                    textLabel = "Referencia",
                    keyboardType = KeyboardType.Number,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                        }
                    ),
                    imeAction = ImeAction.Next
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onCancel,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = cancelText)
                    }
                    Button(
                        onClick = { onConfirm(bankValue.value, phoneValue.value, referenceValue.value) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text(text = confirmText)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ConfirmationDialogPreview() {

    val value = rememberSaveable{ mutableStateOf("") }

    ConfirmationDialog(
        title = "Are you sure?",
        message = "Do you want to delete this item?",
        confirmText = "Yes",
        cancelText = "No",
        onConfirm = { bank, phoneNumber, referenceNumber ->

        },
        onCancel = {},
        price = 50.0,
        value,
        value,
        value
    )
}
