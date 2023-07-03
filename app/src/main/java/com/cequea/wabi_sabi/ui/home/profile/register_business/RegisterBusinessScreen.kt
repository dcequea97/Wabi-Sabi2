package com.cequea.wabi_sabi.ui.home.profile.register_business

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
import com.cequea.wabi_sabi.ui.model.RegisterBusiness
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RegisterBusinessScreen(
    onRegisterSuccessfully: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterBusinessViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val nameValue = remember { mutableStateOf("") }
    val backgroundImageUrlValue = remember { mutableStateOf("") }
    val profileImageUrlValue = remember { mutableStateOf("") }
    val taglineValue = remember { mutableStateOf("") }
    val workingDaysValue = remember { mutableStateOf(listOf(1, 2, 3, 4, 5)) }
    val openingHoursValue = remember { mutableStateOf("") }
    val closingHoursValue = remember { mutableStateOf("") }

    val isLoading by remember(viewModel::isLoading)

    val registerSuccessfully by viewModel.registerSuccessfully.observeAsState(false)

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

    LaunchedEffect(registerSuccessfully) {
        if (registerSuccessfully) {
            onRegisterSuccessfully()
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
                    text = context.getString(R.string.register_business),
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
                    textFieldValue = nameValue,
                    textLabel = context.getString(R.string.business_name),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = backgroundImageUrlValue,
                    textLabel = context.getString(R.string.background_image_url),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = profileImageUrlValue,
                    textLabel = context.getString(R.string.profile_image_url),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = taglineValue,
                    textLabel = context.getString(R.string.tagline),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                WorkingDaysPicker(
                    workingDaysValue = workingDaysValue,
                    onWorkingDaysChanged = { workingDaysValue.value = it }
                )

                TransparentTextField(
                    textFieldValue = openingHoursValue,
                    textLabel = context.getString(R.string.opening_hours),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = closingHoursValue,
                    textLabel = context.getString(R.string.closing_hours),
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.clearFocus() }
                    ),
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(16.dp))

                RoundedButton(
                    text = "Enviar Solicitud",
                    onClick = {
                        val business = RegisterBusiness(
                            id = null,
                            name = nameValue.value,
                            backgroundImageUrl = backgroundImageUrlValue.value,
                            profileImageUrl = profileImageUrlValue.value,
                            tagline = taglineValue.value,
                            workingDays = workingDaysValue.value,
                            openingHours = openingHoursValue.value,
                            closingHours = closingHoursValue.value
                        )
                        viewModel.registerBusiness(business)
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
        RegisterBusinessScreen(
            onRegisterSuccessfully = {
                // Handle adding the new address
            },
            onBack = {}
        )
    }
}