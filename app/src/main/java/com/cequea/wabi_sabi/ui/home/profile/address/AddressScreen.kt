package com.cequea.wabi_sabi.ui.home.profile.address

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.ui.components.CircularIndeterminateProgressBar
import com.cequea.wabi_sabi.ui.components.RoundedButton
import com.cequea.wabi_sabi.ui.components.TransparentTextField
import com.cequea.wabi_sabi.ui.model.Address
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AddressScreen(
    onAddAddressSuccessfully: () -> Unit,
    onBack: () -> Unit,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val addressNameValue = remember { mutableStateOf("") }
    val addressDetailValue = remember { mutableStateOf("") }
    val stateValue = remember { mutableStateOf("") }
    val cityValue = remember { mutableStateOf("") }
    val referencePointValue = remember { mutableStateOf("") }
    val defaultAddressValue = remember { mutableStateOf(false) }

    val isLoading by remember(viewModel::isLoading)

    val registerAddressSuccessfully by viewModel.registerAddressSuccessfully.observeAsState(false)

    val focusManager = LocalFocusManager.current

    LaunchedEffect(viewModel.loadError) {
        withContext(Dispatchers.Main) {
            viewModel.loadError.collect { error ->
                if (!error.isNullOrEmpty()){
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(registerAddressSuccessfully) {
        if (registerAddressSuccessfully) {
            onAddAddressSuccessfully()
        }
    }


    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        onBack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = MaterialTheme.colors.primary
                    )
                }

                Text(
                    text = context.getString(R.string.add_address),
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransparentTextField(
                    textFieldValue = addressNameValue,
                    textLabel = context.getString(R.string.address_name),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = addressDetailValue,
                    textLabel = context.getString(R.string.address_detail),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = stateValue,
                    textLabel = context.getString(R.string.state),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = cityValue,
                    textLabel = context.getString(R.string.city),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = referencePointValue,
                    textLabel = context.getString(R.string.reference_point),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.clearFocus() }
                    ),
                    imeAction = ImeAction.Done
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = defaultAddressValue.value,
                        onCheckedChange = { defaultAddressValue.value = it }
                    )

                    Text(
                        text = "Set as Default Address"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                RoundedButton(
                    text = "Add Address",
                    onClick = {
                        val address = Address(
                            id = null,
                            addressName = addressNameValue.value,
                            addressDetail = addressDetailValue.value,
                            state = stateValue.value,
                            city = cityValue.value,
                            referencePoint = referencePointValue.value,
                            default = defaultAddressValue.value
                        )
                        viewModel.addAddress(address)
                    }
                )
            }
        }
    }

    CircularIndeterminateProgressBar(isDisplayed = isLoading)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAddressScreen() {
    WabiSabiTheme {
        AddressScreen(
            onAddAddressSuccessfully = {
                // Handle adding the new address
            },
            onBack = {}
        )
    }
}