package com.compose.composelearning.di.modules

import android.preference.PreferenceManager
import com.compose.composelearning.data.repository.MainRepository
import com.compose.composelearning.data.repository.MainRepositoryImpl
import com.compose.composelearning.domain.MainUseCase
import com.compose.composelearning.domain.MainUseCaseImpl
import com.compose.composelearning.ui.main.MainActivity
import com.compose.composelearning.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
    single<MainRepository> {
        MainRepositoryImpl(
            get()
        )
    }
    single<MainUseCase> {
        MainUseCaseImpl(
            get()
        )
    }
    viewModel {
        MainViewModel(
            get(),
            androidApplication()
        )
    }
//    scope(named<MainActivity>()) {
//        scoped<MainRepository> {
//            MainRepositoryImpl(
//                get()
//            )
//        }
//        scoped<MainUseCase> {
//            MainUseCaseImpl(
//                get()
//            )
//        }
//        viewModel {
//            MainViewModel(get(), get())
//        }
//    }
//    viewModel {
//        MainViewModel(get(), get())
//    }
}