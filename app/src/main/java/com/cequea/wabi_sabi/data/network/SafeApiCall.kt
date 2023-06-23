package com.cequea.wabi_sabi.data.network


import com.cequea.wabi_sabi.data.model.responses.UniversalResponse
import retrofit2.Response
import javax.inject.Inject

class SafeApiCall @Inject constructor() {
    inline fun <T> safeCall(apiCall: () -> Response<T>): UniversalResponse<T> {
        return try {
            val response = apiCall.invoke()
            UniversalResponse.success(response, response.code())
        } catch (e: Exception) {
            UniversalResponse.failure(e)
        }
    }
}