package com.cequea.wabi_sabi.ui.login

import androidx.annotation.StringRes

data class LoginState(
    val email: String = "",
    val password: String = "",
    val idProfile: Long = 3,
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)