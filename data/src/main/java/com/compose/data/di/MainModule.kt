package com.compose.data.di

import com.compose.data.remote.ApiService
import com.compose.data.repository.MainRepositoryImpl
import com.compose.domain.MainRepository
import com.compose.domain.usecases.MainUseCase
import com.compose.domain.usecases.MainUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideMainRepo(apiService: ApiService): MainRepository =
        MainRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideMainUseCase(repository: MainRepository): MainUseCase =
        MainUseCaseImpl(repository)
}