package com.compose.composelearning.ui.main

import app.cash.turbine.test
import com.compose.data.remote.ApiService
import com.compose.data.repository.MainRepositoryImpl
import com.compose.domain.common.State
import com.compose.domain.model.RecipeModel
import com.compose.domain.model.RecipesModel
import com.compose.domain.usecases.MainUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    @Mock
    private lateinit var useCaseImpl: MainUseCaseImpl
    private val _uiState = MutableStateFlow<State<RecipesModel>>(
        State.Loading)

    @Before
    fun setUp() {
        viewModel = MainViewModel(useCaseImpl)
    }

    @Test
    fun `get recipes from api`(): Unit = runBlocking {
        useCaseImpl.getData().map { state ->
            state.runCatching {
                _uiState.value = this
            }
        }
    }
}