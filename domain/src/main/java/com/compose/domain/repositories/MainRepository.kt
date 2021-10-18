package com.compose.domain.repositories

import com.compose.domain.common.BaseRepository
import com.compose.domain.common.State
import com.compose.domain.converter.toModel
import com.compose.domain.entities.RecipesModel
import com.compose.domain.remote.ApiService
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getData(): Flow<State<RecipesModel>>
}

class MainRepositoryImpl(
    private val apiService: ApiService
) : MainRepository, BaseRepository() {
    override fun getData(): Flow<State<RecipesModel>> = apiCall {
        apiService.getRandom(10,"56cefe88b9b74b97b0b71ef83c2f0584").toModel()
    }
}