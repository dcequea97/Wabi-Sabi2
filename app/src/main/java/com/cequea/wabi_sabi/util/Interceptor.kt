package com.cequea.wabi_sabi.util

import okhttp3.Interceptor
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept:", "application/json")
            .addHeader("ApiKey", "Hola")
            .build()
        return chain.proceed(request)
    }
}