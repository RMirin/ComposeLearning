package com.compose.composelearning

import android.app.Application
import android.content.Context
import com.compose.composelearning.di.modules.appServiceModule
import com.compose.composelearning.di.modules.mainModule
import com.compose.composelearning.di.modules.okHttpModule
import com.compose.composelearning.di.modules.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = this

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)

            modules(
                listOf(
                    appServiceModule,
                    okHttpModule,
                    retrofitModule,
                    mainModule
                )
            )
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}