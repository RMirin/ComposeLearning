package com.compose.composelearning.di.modules

import android.content.Context
import android.net.ConnectivityManager
import com.compose.composelearning.BuildConfig
import com.compose.domain.remote.ApiService
import com.compose.domain.service.NetworkService
import com.compose.domain.service.NetworkServiceImpl
import com.compose.domain.remote.interceptor.AppVersionInterceptor
import com.compose.domain.remote.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val QUALIFIER_OK_HTTP_BACKEND = "BackendHttpClient"
private const val QUALIFIER_RETROFIT_BACKEND = "BackendRetrofit"

val appServiceModule = module {
    single {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single<NetworkService> {
        NetworkServiceImpl(get())
    }
}

val okHttpModule = module {
    single(named(QUALIFIER_OK_HTTP_BACKEND)) {
        val httpClientBuilder = OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(AppVersionInterceptor())
            .addInterceptor(NetworkConnectionInterceptor(get()))

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        httpClientBuilder.build()
    }
}

val retrofitModule = module {
    single(named(QUALIFIER_RETROFIT_BACKEND)) {
        val baseRemoteUrl = "https://api.spoonacular.com/"

        Retrofit.Builder()
            .baseUrl(baseRemoteUrl)
            .client(get(named(QUALIFIER_OK_HTTP_BACKEND)))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    single<ApiService> {
        get<Retrofit>(named(QUALIFIER_RETROFIT_BACKEND)).create(ApiService::class.java)
    }
}