package com.compose.domain.repositories

import com.compose.domain.common.State
import com.compose.domain.entities.RecipesModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getData(): Flow<State<RecipesModel>>
}