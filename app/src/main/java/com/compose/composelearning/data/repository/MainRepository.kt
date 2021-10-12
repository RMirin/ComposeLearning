package com.compose.composelearning.data.repository

import com.compose.composelearning.data.remote.ApiService
import com.compose.composelearning.data.response.RecipesResponse
import com.compose.composelearning.ui.base.BaseRepository
import com.compose.composelearning.ui.base.State
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getData(): Flow<State<RecipesResponse>>
}

class MainRepositoryImpl(
    private val apiService: ApiService
) : MainRepository, BaseRepository() {
    override fun getData(): Flow<State<RecipesResponse>> = apiCall {
        apiService.getRandom(10,"56cefe88b9b74b97b0b71ef83c2f0584")
    }
}