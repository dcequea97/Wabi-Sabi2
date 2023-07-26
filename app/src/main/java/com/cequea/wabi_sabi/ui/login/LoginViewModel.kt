package com.cequea.wabi_sabi.ui.login

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNotNull
import com.cequea.wabi_sabi.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    val state: MutableState<LoginState> = mutableStateOf(LoginState())
    fun login(email: String, password: String) {
        val errorMessage = if(email.isBlank() || password.isBlank()) {
            R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_not_a_valid_email
        } else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)
            val response = repository.login(email, password)
            if (response.data.isNotNull()){
                state.value = state.value.copy(idProfile = response.data!!.data.idProfile)
                state.value = state.value.copy(email = email, password = password)
                state.value = state.value.copy(successLogin = true)
                response.data.message?.let {
                    state.value = state.value.copy(errorMessage = R.string.error_user_or_password)
                }
            }else {
                state.value = state.value.copy(errorMessage = R.string.universal_error)
            }
            state.value = state.value.copy(displayProgressBar = false)

        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}