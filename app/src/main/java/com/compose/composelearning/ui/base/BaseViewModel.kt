package com.compose.composelearning.ui.base

import android.app.Application
import android.os.RemoteException
import androidx.lifecycle.ViewModel
import com.compose.composelearning.BuildConfig
import com.compose.composelearning.R
import com.compose.composelearning.util.Event
import com.compose.data.exception.ClientException
import com.compose.data.exception.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(application: Application) : ViewModel() {

    protected val context = application.applicationContext

    private val _errorMessage = MutableSharedFlow<Event<String>>()
    val errorMessage: SharedFlow<Event<String>> get() = _errorMessage

    protected fun getBasicErrorMsg(throwable: Throwable): Event<String> {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
//        viewModelScope.launch {
            return when (throwable) {
                is RemoteException -> Event(throwable.message ?: context.getString(R.string.alert_undefined))
                is NoInternetException -> Event(context.getString(R.string.alert_no_internet))
                is ClientException -> Event(throwable.error)
                else -> Event(context.getString(when (throwable) {
                    is UnknownHostException,
                    is SocketTimeoutException,
                    is ConnectException -> R.string.alert_no_server_connection
                    else -> R.string.alert_undefined
                }))
//            })
        }
    }

}