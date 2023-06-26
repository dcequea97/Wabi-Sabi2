package com.cequea.wabi_sabi.data.model.responses

import com.cequea.wabi_sabi.data.model.UserModel

data class LoginResponse(
    val data: UserModel,
    val token: String
)