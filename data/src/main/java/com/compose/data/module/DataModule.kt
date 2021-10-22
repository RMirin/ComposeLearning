package com.compose.data.module

import android.content.Context
import android.net.ConnectivityManager
import com.compose.data.BuildConfig
import com.compose.data.remote.ApiService
import com.compose.data.remote.interceptor.NetworkConnectionInterceptor
import com.compose.data.service.NetworkService
import com.compose.data.service.NetworkServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideNetworkService(@ApplicationContext context: Context): NetworkService =
        NetworkServiceImpl(
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        )

    @Provides
    @Singleton
    fun provideOkHttpClient(networkService: NetworkService): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(NetworkConnectionInterceptor(networkService))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}