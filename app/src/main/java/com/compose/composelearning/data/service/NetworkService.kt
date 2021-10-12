package com.compose.composelearning.data.service

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

interface NetworkService {
    val isNetworkAvailable: Boolean
}

@Suppress("DEPRECATION")
class NetworkServiceImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkService {

    override val isNetworkAvailable: Boolean
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

                networkCapabilities?.hasNetworkCapability == true
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo

                networkInfo?.isConnected == true
            }
        }

    private val NetworkCapabilities.hasNetworkCapability: Boolean
        get() = hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)

}