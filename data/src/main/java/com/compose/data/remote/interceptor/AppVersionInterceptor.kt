package com.compose.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AppVersionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header(HEADER_X_APP_VERSION,"$OS${1.0}")
        val newRequest = requestBuilder.build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val OS = "android:"
        private const val HEADER_X_APP_VERSION = "X-App-Version"
    }

}