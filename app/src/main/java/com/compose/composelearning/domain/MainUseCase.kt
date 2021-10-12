package com.compose.composelearning.domain

import com.compose.composelearning.data.repository.MainRepository
import com.compose.composelearning.data.response.RecipesResponse
import com.compose.composelearning.ui.base.State
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getData(): Flow<State<RecipesResponse>>
}

class MainUseCaseImpl(
    private val mainRepository: MainRepository
) : MainUseCase {
    override fun getData(): Flow<State<RecipesResponse>> = mainRepository.getData()

}