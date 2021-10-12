package com.compose.composelearning.data.remote.interceptor

import com.compose.composelearning.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AppVersionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header(HEADER_X_APP_VERSION,"$OS${BuildConfig.VERSION_NAME}")
        val newRequest = requestBuilder.build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val OS = "android:"
        private const val HEADER_X_APP_VERSION = "X-App-Version"
    }

}