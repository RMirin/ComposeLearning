package com.compose.composelearning.di.modules

import com.compose.composelearning.ui.main.MainViewModel
import com.compose.domain.repositories.MainRepository
import com.compose.domain.repositories.MainRepositoryImpl
import com.compose.domain.usecases.MainUseCase
import com.compose.domain.usecases.MainUseCaseImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
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
}