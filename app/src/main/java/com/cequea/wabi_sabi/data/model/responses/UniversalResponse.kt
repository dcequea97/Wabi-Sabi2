package com.cequea.wabi_sabi.data.model.responses

import retrofit2.Response
import java.lang.Exception

data class UniversalResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?,
    val statusCode: Int?
) {

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    companion object{
        fun <T> success (data: Response<T>, statusCode: Int): UniversalResponse<T> {
            return UniversalResponse(
                Status.Success,
                data,
                null,
                statusCode
            )
        }
        fun <T> failure (exception: Exception): UniversalResponse<T> {
            return UniversalResponse(
                Status.Failure,
                null,
                exception,
                null
            )
        }
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = !failed && this.data?.isSuccessful == true

    val body: T
        get() = this.data!!.body()!!
}