package com.compose.domain.usecases

import com.compose.domain.common.State
import com.compose.domain.entities.RecipesModel
import com.compose.domain.repositories.MainRepository
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getData(): Flow<State<RecipesModel>>
}

class MainUseCaseImpl(
    private val mainRepository: MainRepository
) : MainUseCase {
    override fun getData(): Flow<State<RecipesModel>> = mainRepository.getData()
}