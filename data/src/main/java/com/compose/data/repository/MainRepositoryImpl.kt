package com.compose.data.repository

import com.compose.data.converter.toModel
import com.compose.data.remote.ApiService
import com.compose.domain.common.BaseRepository
import com.compose.domain.common.State
import com.compose.domain.entities.RecipesModel
import com.compose.domain.repositories.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MainRepository, BaseRepository() {
    override fun getData(): Flow<State<RecipesModel>> = apiCall {
        apiService.getRandom(10,"56cefe88b9b74b97b0b71ef83c2f0584").toModel()
    }
}