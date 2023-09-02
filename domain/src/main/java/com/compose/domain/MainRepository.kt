package com.compose.domain

import com.compose.domain.common.State
import com.compose.domain.model.RecipesModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getData(): Flow<State<RecipesModel>>
}