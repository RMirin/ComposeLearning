package com.compose.composelearning.ui.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.compose.composelearning.ui.base.BaseViewModel
import com.compose.composelearning.util.doOnError
import com.compose.composelearning.util.doOnLoading
import com.compose.composelearning.util.doOnSuccess
import com.compose.domain.common.State
import com.compose.domain.entities.RecipeModel
import com.compose.domain.usecases.MainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

class MainViewModel(
    private val mainUseCase: MainUseCase,
    private val app: Application
): BaseViewModel(app) {

    private val _uiState = MutableStateFlow<State<List<RecipeModel>>>(State.Loading)
    val uiState: StateFlow<State<List<RecipeModel>>>
        get() = _uiState

    fun getData() = mainUseCase.getData()
        .doOnLoading {
            showErrorState.set(true)
        }
        .doOnSuccess { dto ->
            _uiState.value = State.Success(dto.recipes)
        }
        .doOnError { error ->
            showErrorState.set(false)
            showBasicError(error)
        }
        .launchIn(viewModelScope)
}