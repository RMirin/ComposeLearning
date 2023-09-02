package com.compose.domain.usecases

import com.compose.domain.MainRepository
import com.compose.domain.common.State
import com.compose.domain.model.RecipesModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface MainUseCase {
    fun getData(): Flow<State<RecipesModel>>
}

@Singleton
class MainUseCaseImpl @Inject constructor(
     private val mainRepository: MainRepository
) : MainUseCase {
    override fun getData(): Flow<State<RecipesModel>> {
        return mainRepository.getData()
    }
}